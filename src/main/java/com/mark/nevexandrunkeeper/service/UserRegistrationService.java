package com.mark.nevexandrunkeeper.service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.model.entity.OAuthUserEntity;
import com.mark.nevexandrunkeeper.model.entity.UserEntity;
import com.mark.nevexandrunkeeper.model.runkeeper.RunKeeperProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by NeVeX on 7/4/2016.
 */
@Service
public class UserRegistrationService {

    @Autowired
    private RunKeeperService runKeeperService;

    @Value("${oauth.client-id}")
    private String oauthClientId;
    @Value("${oauth.redirect-url}")
    private String oauthRedirectUrl;
    @Value("${application.oauth-access-token}")
    private String applicationAccessToken;
    @Value("${application.user-id}")
    private long applicationUserId;

    public UserEntity register(String oauthCode) {
        if ( StringUtils.hasText(oauthCode)) {

            OAuthUserEntity oAuthUserEntityToSave = new OAuthUserEntity();
            oAuthUserEntityToSave.setCreatedDate(new Date());
            oAuthUserEntityToSave.setOauthCode(oauthCode);
            oAuthUserEntityToSave.setRedirectUrl(oauthRedirectUrl);
            oAuthUserEntityToSave.setOauthClientId(oauthClientId); // save the client id used for this
            oAuthUserEntityToSave.setActive(true);

//            Key<OAuthUserEntity> result = ObjectifyService.ofy().save().entity(oAuthUserEntityToSave).now();

//            OAuthUserEntity savedEntity = ObjectifyService.ofy().load().key(result).now();

            // try and save the access token
            String accessToken = runKeeperService.getAccessToken(oAuthUserEntityToSave.getOauthCode());
            if ( StringUtils.hasText(accessToken)) {
                oAuthUserEntityToSave.setAccessToken(accessToken);
//                ObjectifyService.ofy().save().entity(savedEntity).now();

                // get user information now
                Long userId = runKeeperService.getUserId(accessToken);

                if ( userId != null ) {

                    // Get all other users and mark them as invalid now
                    List<OAuthUserEntity> existingSignUps = ObjectifyService.ofy().load().type(OAuthUserEntity.class).filter("userId", userId).list();
                    // this code should be extracted out
                    if ( existingSignUps != null && !existingSignUps.isEmpty()) {
                        for (OAuthUserEntity e : existingSignUps ) {
                            if ( e.isActive() ) {
                                e.setActive(false);
                                e.setUpdatedDate(new Date());
                                ObjectifyService.ofy().save().entity(e).now();
                            }
                        }
                    }

                    // save this user id to the oauth entity
                    oAuthUserEntityToSave.setUserId(userId);
                    oAuthUserEntityToSave.setActive(true); // set it active now

                    RunKeeperProfileResponse profile = runKeeperService.getProfileInformation(accessToken);
                    if (profile != null) {

                        // See if we have this user already
                        UserEntity userEntityToSave = getUserEntity(userId);
                        if ( userEntityToSave == null ) {
                            userEntityToSave = new UserEntity();
                        }
                        userEntityToSave.setAthleteType(profile.getAthleteType());
                        userEntityToSave.setUserId(userId);
                        userEntityToSave.setBirthday(profile.getBirthday());
                        userEntityToSave.setGender(profile.getGender());
                        userEntityToSave.setLocation(profile.getLocation());
                        userEntityToSave.setName(profile.getName());

                        // Now we need to become friends with this person
//                        Long applicationUserId = runKeeperService.getUserId(applicationAccessToken);
                        // add application to the friends of this user
                        boolean requestSent = runKeeperService.sendFriendRequest(accessToken, applicationUserId);

                        oAuthUserEntityToSave.setFriendRequestSent(requestSent);
                        // save the oauth user information
                        ObjectifyService.ofy().save().entity(oAuthUserEntityToSave).now();
                        // try and save the user information too
                        ObjectifyService.ofy().save().entity(userEntityToSave).now();

                        return userEntityToSave;
                    }
                }
            }
        }
        return null;
    }

    private UserEntity getUserEntity(long userId) {
        Key<UserEntity> key = Key.create(UserEntity.class, userId);
        return ObjectifyService.ofy().load().key(key).now();
    }

    public void unregister(String oAuthCode) {
        if (StringUtils.hasText(oAuthCode)) {
            // find this oauth code
            List<OAuthUserEntity> registeredCodes = ObjectifyService.ofy().load().type(OAuthUserEntity.class).filter("oauthCode", oAuthCode).list();
            if ( registeredCodes != null && !registeredCodes.isEmpty()) {
                // de-activate each one
                for ( OAuthUserEntity entity : registeredCodes ) {
                    entity.setUpdatedDate(new Date());
                    entity.setActive(false);
                    ObjectifyService.ofy().save().entity(entity).now();
                }

            }
        }
    }

    public Set<User> getActiveUsers() {
        Set<User> activeRegistrations = new HashSet<>();
        List<OAuthUserEntity> registrations = ObjectifyService.ofy().load().type(OAuthUserEntity.class).list();
        if ( registrations != null && !registrations.isEmpty()) {
            for ( OAuthUserEntity entity : registrations) { //47002962
                if ( entity.isActive() && entity.getUserId() != null ) {

                    if ( !entity.isFriend() ) {
                        // See if the friend request should be sent (legacy data may miss this)
                        if (!entity.isFriendRequestSent()) {
                            boolean requestSent = runKeeperService.sendFriendRequest(entity.getAccessToken(), applicationUserId);
                            if (requestSent) {
                                entity.setFriendRequestSent(true);
                                ObjectifyService.ofy().save().entity(entity);
                            }
                        }
                        // re-heck if we should check if we are friends now
                        if (entity.isFriendRequestSent()) {
                            // see if we are friends now
                            boolean areFriends = runKeeperService.isFriend(applicationAccessToken, entity.getUserId());
                            if (areFriends) {
                                entity.setFriend(true);
                                ObjectifyService.ofy().save().entity(entity); // save this new fact
                            }
                        }
                    }
                    if ( entity.isFriend()) { // Recheck since we could of connected above
                        UserEntity ue = getUserEntity(entity.getUserId());
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
