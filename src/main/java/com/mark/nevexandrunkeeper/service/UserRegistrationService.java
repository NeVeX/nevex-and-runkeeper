package com.mark.nevexandrunkeeper.service;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.dao.OAuthUsersRepository;
import com.mark.nevexandrunkeeper.dao.RunKeeperUsersRepository;
import com.mark.nevexandrunkeeper.dao.entity.OAuthUserEntity;
import com.mark.nevexandrunkeeper.dao.entity.RunKeeperUserEntity;
import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.model.runkeeper.RunKeeperProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by NeVeX on 7/4/2016.
 */
@Service
public class UserRegistrationService {

    private final RunKeeperService runKeeperService;
    private final OAuthUsersRepository oAuthUsersRepository;
    private final RunKeeperUsersRepository runKeeperUsersRepository;
    private final String oauthClientId;
    private final String oauthRedirectUrl;
    private final String applicationAccessToken;
    private final int applicationUserId;

    @Autowired
    public UserRegistrationService(ApplicationProperties applicationProperties,
                                   RunKeeperService runKeeperService,
                                   OAuthUsersRepository oAuthUsersRepository,
                                   RunKeeperUsersRepository runKeeperUsersRepository) {
        this.oauthClientId = applicationProperties.getOauth().getClientId();
        this.oauthRedirectUrl = applicationProperties.getOauth().getRedirectUrl();
        this.applicationAccessToken = applicationProperties.getOauth().getAccessToken();
        this.applicationUserId = applicationProperties.getUserId();
        this.runKeeperService = runKeeperService;
        this.oAuthUsersRepository = oAuthUsersRepository;
        this.runKeeperUsersRepository = runKeeperUsersRepository;
    }

    public RunKeeperUserEntity register(String oauthCode) {
        if ( StringUtils.hasText(oauthCode)) {

            OAuthUserEntity oAuthUserEntityToSave = new OAuthUserEntity();
            oAuthUserEntityToSave.setCreatedDate(new Date());
            oAuthUserEntityToSave.setOauthCode(oauthCode);
            oAuthUserEntityToSave.setRedirectUrl(oauthRedirectUrl);
            oAuthUserEntityToSave.setOauthClientId(oauthClientId); // save the client id used for this
            oAuthUserEntityToSave.setActive(true);

            // try and save the access token
            String accessToken = runKeeperService.getAccessToken(oAuthUserEntityToSave.getOauthCode());
            if ( StringUtils.hasText(accessToken)) {
                oAuthUserEntityToSave.setAccessToken(accessToken);
                // get user information now
                Integer userId = runKeeperService.getUserId(accessToken);

                if ( userId != null ) {

                    // Get all other users and mark them as invalid now
                    List<OAuthUserEntity> existingSignUps = oAuthUsersRepository.findByUserId(userId);
//                            ObjectifyService.ofy().load().type(OAuthUserEntity.class).filter("userId", userId).list();

                    // this code should be extracted out
                    if ( existingSignUps != null && !existingSignUps.isEmpty()) {
                        for (OAuthUserEntity e : existingSignUps ) {
                            if ( e.isActive() ) {
                                e.setActive(false);
                                e.setUpdatedDate(new Date());
//                                ObjectifyService.ofy().save().entity(e).now();
                                oAuthUsersRepository.save(e);
                            }
                        }
                    }

                    // save this user id to the oauth entity
                    oAuthUserEntityToSave.setUserId(userId);
                    oAuthUserEntityToSave.setActive(true); // set it active now

                    RunKeeperProfileResponse profile = runKeeperService.getProfileInformation(accessToken);
                    if (profile != null) {

                        // See if we have this user already
                        RunKeeperUserEntity runKeeperUserEntityToSave = getUserEntity(userId);
                        if ( runKeeperUserEntityToSave == null ) {
                            runKeeperUserEntityToSave = new RunKeeperUserEntity();
                        }
                        runKeeperUserEntityToSave.setAthleteType(profile.getAthleteType());
                        runKeeperUserEntityToSave.setUserId(userId);
                        runKeeperUserEntityToSave.setBirthday(profile.getBirthday());

                        runKeeperUserEntityToSave.setGender(profile.getGender());
                        runKeeperUserEntityToSave.setLocation(profile.getLocation());
                        runKeeperUserEntityToSave.setName(profile.getName());

                        // add application to the friends of this user
                        boolean requestSent = runKeeperService.sendFriendRequest(accessToken, applicationUserId);

                        oAuthUserEntityToSave.setFriendRequestSent(requestSent);

                        oAuthUsersRepository.save(oAuthUserEntityToSave);
                        // save the oauth user information
//                        ObjectifyService.ofy().save().entity(oAuthUserEntityToSave).now();
                        // try and save the user information too
                        runKeeperUsersRepository.save(runKeeperUserEntityToSave);
//                        ObjectifyService.ofy().save().entity(runKeeperUserEntityToSave).now();

                        return runKeeperUserEntityToSave;
                    }
                }
            }
        }
        return null;
    }

    private RunKeeperUserEntity getUserEntity(int userId) {
//        Key<RunKeeperUserEntity> key = Key.create(RunKeeperUserEntity.class, userId);
        Optional<RunKeeperUserEntity> optionalUser = runKeeperUsersRepository.findByUserId(userId);
        if ( optionalUser.isPresent() ) {
            return optionalUser.get();
        }
        return null;
//        return ObjectifyService.ofy().load().key(key).now();
    }

    public void unregister(String oAuthCode) {
        if (StringUtils.hasText(oAuthCode)) {
            // find this oauth code
            List<OAuthUserEntity> registeredCodes = oAuthUsersRepository.findByOAuthCode(oAuthCode);
                    // ObjectifyService.ofy().load().type(OAuthUserEntity.class).filter("oauthCode", oAuthCode).list();
            if ( registeredCodes != null && !registeredCodes.isEmpty()) {
                // de-activate each one
                for ( OAuthUserEntity entity : registeredCodes ) {
                    entity.setUpdatedDate(new Date());
                    entity.setActive(false);
//                    ObjectifyService.ofy().save().entity(entity).now();
                    oAuthUsersRepository.save(entity);
                }

            }
        }
    }

    public Set<User> getActiveUsers() {
        Set<User> activeRegistrations = new HashSet<>();
        Iterable<OAuthUserEntity> registrations = oAuthUsersRepository.findAll();
//                ObjectifyService.ofy().load().type(OAuthUserEntity.class).list();
        if ( registrations != null ) {
            for ( OAuthUserEntity entity : registrations) { //47002962
                if ( entity.isActive() ) {

                    if ( !entity.isFriend() ) {
                        // See if the friend request should be sent (legacy data may miss this)
                        if (!entity.isFriendRequestSent()) {
                            boolean requestSent = runKeeperService.sendFriendRequest(entity.getAccessToken(), applicationUserId);
                            if (requestSent) {
                                entity.setFriendRequestSent(true);
//                                ObjectifyService.ofy().save().entity(entity);
                                oAuthUsersRepository.save(entity);
                            }
                        }
                        // re-heck if we should check if we are friends now
                        if (entity.isFriendRequestSent()) {
                            // see if we are friends now
                            boolean areFriends = runKeeperService.isFriend(applicationAccessToken, entity.getUserId());
                            if (areFriends) {
                                entity.setFriend(true);
//                                ObjectifyService.ofy().save().entity(entity); // save this new fact
                                oAuthUsersRepository.save(entity);
                            }
                        }
                    }
                    if ( entity.isFriend()) { // Recheck since we could of connected above
                        RunKeeperUserEntity ue = getUserEntity(entity.getUserId());
                        User u = new User();
                        u.setUserId(ue.getUserId());
                        u.setName(ue.getName());
                        u.setAccessToken(entity.getAccessToken());
                        u.setLocation(ue.getLocation());
                        u.setAthleteType(ue.getAthleteType());
                        u.setBirthday(ue.getBirthday());
                        u.setGender(ue.getGender());
                        u.setSignUpDate(ue.getSignUpDate());
                        // Add this to the list of registrations
                        activeRegistrations.add(u);
                    }
                }
            }
        }
        return activeRegistrations;
    }

}
