package com.mark.nevexandrunkeeper.runkeeper;

import com.mark.nevexandrunkeeper.runkeeper.api.RunKeeperAPIClient;
import com.mark.nevexandrunkeeper.runkeeper.api.model.RunKeeperFriendsReplyResponse;
import com.mark.nevexandrunkeeper.runkeeper.api.model.RunKeeperFriendsReplyWrapperResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NeVeX on 7/6/2016.
 */
@Service
class RunKeeperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunKeeperService.class);
    private final RunKeeperAPIClient apiClient;

    @Autowired
    RunKeeperService(RunKeeperAPIClient apiClient) {
        this.apiClient = apiClient;
    }



    boolean isFriend(String accessToken, long userId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/vnd.com.model.Reply+json");
        headers.put("Authorization", "Bearer "+accessToken);
        try {
            boolean stillMoreFriendsToTry = true;
            String urlToInvoke = runKeeperApiBaseUrl + "/team"; // need to build it here since we'll use the API url for pagination
            while (stillMoreFriendsToTry) {
                RunKeeperFriendsReplyWrapperResponse response = RunKeeperAPIClient.execute(urlToInvoke, headers, "GET", RunKeeperFriendsReplyWrapperResponse.class);
                if ( response != null && response.getItems() != null && !response.getItems().isEmpty()) {
                    List<RunKeeperFriendsReplyResponse> friends = response.getItems();
                    for ( RunKeeperFriendsReplyResponse f : friends) {
                        if ( f.getUserId() != null && f.getUserId().equals(userId)) {
                            // FRRIEENNNNNDD!!!
                            return true;
                        }
                    }
                    // see if we have more friends
                    if ( StringUtils.hasText(response.getNextUri())) {
                        urlToInvoke = runKeeperApiBaseUrl + response.getNextUri();
                        LOGGER.warn("Did not find friends in request but there are more friends to check - using the uri next [{}]", urlToInvoke);
                    } else {
                        stillMoreFriendsToTry = false;
                    }
                } else {
                    stillMoreFriendsToTry = false;
                }
            }
        } catch (Exception e ) {
            LOGGER.error("Something went wrong determining if token ["+accessToken+"] is a friend of userId ["+userId+"] using the RunKeeper api. Reason [{}]", e.getMessage());
        }
        return false;
    }



}
