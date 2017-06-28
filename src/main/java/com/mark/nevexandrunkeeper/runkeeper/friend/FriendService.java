package com.mark.nevexandrunkeeper.runkeeper.friend;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.runkeeper.api.RunKeeperAPIClient;
import com.mark.nevexandrunkeeper.runkeeper.api.exception.RunKeeperAPIException;
import com.mark.nevexandrunkeeper.runkeeper.exception.RunKeeperException;
import com.mark.nevexandrunkeeper.runkeeper.oauth.OAuthUserService;
import com.mark.nevexandrunkeeper.runkeeper.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Created by Mark Cunningham on 6/28/2017.
 */
@Service
public class FriendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FriendService.class);

    private final int applicationUserId;
    private final RunKeeperAPIClient apiClient;
    private final UserService userService;
    private final OAuthUserService oAuthUserService;

    @Autowired
    FriendService(ApplicationProperties applicationProperties,
                  RunKeeperAPIClient apiClient,
                  UserService userService,
                  OAuthUserService oAuthUserService) {
        this.applicationUserId = applicationProperties.getUserId();
        this.apiClient = apiClient;
        this.userService = userService;
        this.oAuthUserService = oAuthUserService;
    }

    @Transactional
    public int sendAllFriendRequests() {
        int totalRequestsSent = 0;
        List<User> allActiveFriendsToBeRequested = userService.getAllActiveUsersThatHaveNotGotFriendRequested();
        if ( allActiveFriendsToBeRequested.isEmpty()) {
            return 0; // nothing to do
        }
        for ( User user : allActiveFriendsToBeRequested ) {
            Optional<String> accessTokenOptional = oAuthUserService.getAccessTokenForActiveUserId(user.getUserId());
            if ( accessTokenOptional.isPresent()) {
                LOGGER.info("Sending friend request with access token [{}] (that is userId [{}]) for application id",
                        accessTokenOptional.get(), user.getUserId());
                boolean requestSent = askApplicationToBecomeUsersFriend(accessTokenOptional.get());
                if (requestSent) {
                    totalRequestsSent++;
                    if ( !userService.setFriendRequestSent(user.getUserId()) ) {
                        LOGGER.warn("Could not store that the friend request was sent for user id [{}] - it'll be attempted again later", user.getUserId());
                    }
                }
            }
        }
        return totalRequestsSent;
    }

    private boolean askApplicationToBecomeUsersFriend(String accessToken) {
        try {
            return apiClient.sendFriendRequest(accessToken, applicationUserId);
        } catch (RunKeeperAPIException apiEx) {
            LOGGER.error("Could not send friend request using access token [{}]", accessToken, apiEx);
        }
        return false;
    }


    private boolean sendFriendRequest(String friendAccessToken) throws RunKeeperException {
        try {
            return apiClient.sendFriendRequest(friendAccessToken, applicationUserId);
        } catch (RunKeeperAPIException apiEx) {
            throw new RunKeeperException(apiEx);
        }
    }

}
