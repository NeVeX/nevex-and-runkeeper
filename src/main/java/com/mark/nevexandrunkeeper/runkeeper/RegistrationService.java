package com.mark.nevexandrunkeeper.runkeeper;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.runkeeper.api.RunKeeperAPIClient;
import com.mark.nevexandrunkeeper.runkeeper.api.exception.RunKeeperAPIException;
import com.mark.nevexandrunkeeper.runkeeper.api.model.RunKeeperProfileResponse;
import com.mark.nevexandrunkeeper.runkeeper.exception.RunKeeperException;
import com.mark.nevexandrunkeeper.runkeeper.oauth.OAuthUserService;
import com.mark.nevexandrunkeeper.runkeeper.oauth.model.entity.OAuthUserEntity;
import com.mark.nevexandrunkeeper.runkeeper.user.UserService;
import com.mark.nevexandrunkeeper.runkeeper.user.model.entity.RunKeeperUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by NeVeX on 7/4/2016.
 */
@Service
public class RegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    private final RunKeeperAPIClient runKeeperAPIClient;
    private final OAuthUserService oAuthUserService;
    private final UserService userService;

    @Autowired
    public RegistrationService(RunKeeperAPIClient runKeeperAPIClient,
                               OAuthUserService oAuthUserService,
                               UserService userService) {
        this.runKeeperAPIClient = runKeeperAPIClient;
        this.oAuthUserService = oAuthUserService;
        this.userService = userService;
    }

    @Transactional
    public String registerNewUser(String oauthCode) throws RunKeeperException {

        if (!oAuthUserService.saveNewRegistrationCode(oauthCode)) {
            throw new RunKeeperException("Could not save oauth code [" + oauthCode + "]");
        }

        // Try and get an access token
        Optional<String> accessTokenOptional;
        try {
            accessTokenOptional = runKeeperAPIClient.getAccessTokenForCode(oauthCode);
        } catch (RunKeeperAPIException apiEx ) {
            throw new RunKeeperException(apiEx); // wrapper exception
        }

        if (!accessTokenOptional.isPresent()) {
            throw new RunKeeperException("Could not get an access token for code [" + oauthCode + "]");
        }

        String accessToken = accessTokenOptional.get();
        if (!oAuthUserService.saveAccessTokenForOauthCode(accessToken, oauthCode)) {
            throw new RunKeeperException("Could not save access token [" + accessToken + "] for code [" + oauthCode + "]");
        }
        return accessToken;
    }

    @Transactional
    public User createNewUserAccount(String accessToken) throws RunKeeperException {

        Optional<Integer> userIdOptional;
        try {
            userIdOptional = runKeeperAPIClient.getUserId(accessToken);
        } catch (RunKeeperAPIException apiEx) {
            throw new RunKeeperException(apiEx);
        }

        if ( !userIdOptional.isPresent()) {
            throw new RunKeeperException("Could not get UserId for access token ["+accessToken+"]");
        }
        int userId = userIdOptional.get();
        // So we have a user, but this could be the 2nd, 3rd...time signing up, so we must invalid all other records
        int recordsSetToInActive = oAuthUserService.setAllOAuthRecordsAsInactiveForUserId(userId);
        LOGGER.info("Set [{}] oauth records to inactive for userId [{}]", recordsSetToInActive, userId);

        if ( !oAuthUserService.setActiveOauthForUserAndToken(userId, accessToken)) {
            throw new RunKeeperException("Could not set an active record for userId ["+userId+"] for access token ["+accessToken+"]");
        }


        Optional<RunKeeperProfileResponse> profileOptional;

        try {
            profileOptional = runKeeperAPIClient.getProfileInformation(accessToken);
        } catch (RunKeeperAPIException apiEx) {
            throw new RunKeeperException(apiEx);
        }

        if ( !profileOptional.isPresent()) {
            throw new RunKeeperException("Could not get profile information for userId [" +userId+"]");
        }

        RunKeeperProfileResponse profile = profileOptional.get();

        User user = userService.saveProfile(userId, profile);
        LOGGER.info("A new user has been created in the system [{}]", user);
        return user;
    }

    public boolean unregister(int userId) {
        // Don't really need to change the status of the oauth records, but for now we'll do it
        int oauthCount = oAuthUserService.setAllOAuthRecordsAsInactiveForUserId(userId);
        boolean didUpdateUser = userService.setUserInactive(userId);
        if ( didUpdateUser) {
            LOGGER.info("UserId [{}] has been unregistered. Also a total of [{}] oauth user records were in-activated", userId, oauthCount);
        } else {
            LOGGER.info("Did not unregister userId [{}]", userId); // The id might be bad too...
        }
        return true;
    }

}
