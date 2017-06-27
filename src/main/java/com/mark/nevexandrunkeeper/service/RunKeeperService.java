package com.mark.nevexandrunkeeper.service;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.exception.RunKeeperException;
import com.mark.nevexandrunkeeper.model.runkeeper.*;
import com.mark.nevexandrunkeeper.util.APIUtils;
import com.mark.nevexandrunkeeper.util.HttpClientUtils;
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
public class RunKeeperService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RunKeeperService.class.getName());

    private final String oauthClientId;
    private final String oauthClientSecret;
    private final String oauthRedirectUrl;
    private final String runKeeperApiOauthTokenUrl;
    private final String runKeeperApiUserUrl;
    private final String runKeeperApiProfileUrl;
    private final String runKeeperApiFitnessActivitiesUrl;
    private final String runKeeperApiBaseUrl;

    @Autowired
    public RunKeeperService(ApplicationProperties applicationProperties) {
        this.oauthClientId = applicationProperties.getOauth().getClientId();
        this.oauthClientSecret = applicationProperties.getOauth().getClientSecret();
        this.oauthRedirectUrl = applicationProperties.getOauth().getRedirectUrl();
        this.runKeeperApiOauthTokenUrl = applicationProperties.getOauth().getTokenUrl();
        this.runKeeperApiUserUrl = applicationProperties.getRunkeeperApi().getUserUrl();
        this.runKeeperApiProfileUrl = applicationProperties.getRunkeeperApi().getProfileUrl();
        this.runKeeperApiFitnessActivitiesUrl = applicationProperties.getRunkeeperApi().getFitnessActivitiesUrl();
        this.runKeeperApiBaseUrl = applicationProperties.getRunkeeperApi().getBaseUrl();
    }

    public String getAccessToken(String oauthCode) {
        String url = new StringBuilder()
            .append(runKeeperApiOauthTokenUrl)
            .append("?client_id=").append(oauthClientId)
            .append("&client_secret=").append(oauthClientSecret)
            .append("&redirect_uri=").append(APIUtils.urlEncodeString(oauthRedirectUrl))
            .append("&grant_type=").append("authorization_code")
            .append("&code=").append(oauthCode)
            .toString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        RunKeeperAccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = HttpClientUtils.execute(url, headers, "POST", RunKeeperAccessTokenResponse.class);
        } catch (RunKeeperException e) {
            return null; // catch and ignore for now
        }
        return accessTokenResponse != null ? accessTokenResponse.getAccessToken() : null;
    }

    public Integer getUserId(String accessToken) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/vnd.com.runkeeper.User+json");
        headers.put("Authorization", "Bearer "+accessToken);

        RunKeeperUserResponse rur = null;
        try {
            rur = HttpClientUtils.execute(runKeeperApiUserUrl, headers, "GET", RunKeeperUserResponse.class);
        } catch (Exception e ){
            LOGGER.error("Something went wrong getting user information from runkeeper api. "+e.getMessage());
        }
        return rur != null ? rur.getUserId() : null;
    }

    public boolean sendFriendRequest(String accessToken, long userId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/vnd.com.runkeeper.Invitation+json");
        headers.put("Authorization", "Bearer "+accessToken);
        RunKeeperFriendInvitationRequest invitationRequest = new RunKeeperFriendInvitationRequest();
        invitationRequest.setUserId(userId);
        try {
            HttpClientUtils.execute(runKeeperApiBaseUrl + "/team", headers, "POST", invitationRequest, String.class);
            return true;
        } catch (Exception e ) {
            LOGGER.error("Something went wrong sent a friend request for token ["+accessToken+"] for userId ["+userId+"] runkeeper api. "+e.getMessage());
            return false;
        }
    }

    public boolean isFriend(String accessToken, long userId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/vnd.com.runkeeper.Reply+json");
        headers.put("Authorization", "Bearer "+accessToken);
        try {
            boolean stillMoreFriendsToTry = true;
            String urlToInvoke = runKeeperApiBaseUrl + "/team"; // need to build it here since we'll use the API url for pagination
            while (stillMoreFriendsToTry) {
                RunKeeperFriendsReplyWrapperResponse response = HttpClientUtils.execute(urlToInvoke, headers, "GET", RunKeeperFriendsReplyWrapperResponse.class);
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
                        LOGGER.warn("Did not find friends in request but there are more friends to check - using the uri next ["+urlToInvoke+"]");
                    } else {
                        stillMoreFriendsToTry = false;
                    }
                } else {
                    stillMoreFriendsToTry = false;
                }

            }

        } catch (Exception e ) {
            LOGGER.error("Something went wrong determining if token ["+accessToken+"] is a friend of userId ["+userId+"] using the runkeeper api. "+e.getMessage());

        }
        return false;
    }

    public RunKeeperProfileResponse getProfileInformation(String accessToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/vnd.com.runkeeper.Profile+json");
        headers.put("Authorization", "Bearer "+accessToken);

        RunKeeperProfileResponse rpr = null;
        try {
            rpr = HttpClientUtils.execute(runKeeperApiProfileUrl, headers, "GET", RunKeeperProfileResponse.class);
        } catch (Exception e ) {
            LOGGER.error("Something went wrong getting profile information from runkeeper api. "+e.getMessage());
        }
        return rpr;
    }

    public RunKeeperFitnessActivityResponse getLastFitness(String accessToken) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/vnd.com.runkeeper.FitnessActivity+json");
//        headers.put("Accept", "application/vnd.com.runkeeper.FitnessActivitySummary+json");
        headers.put("Authorization", "Bearer "+accessToken);
        RunKeeperFitnessActivityResponse rpr = null;
        try {
            RunKeeperFitnessActivityListResponse listResponse = HttpClientUtils.execute(runKeeperApiFitnessActivitiesUrl+"?pageSize=1", headers, "GET", RunKeeperFitnessActivityListResponse.class);
            if ( listResponse != null && listResponse.getItems() != null && !listResponse.getItems().isEmpty()) {
                rpr = listResponse.getItems().get(0);
            }
        } catch (Exception e ) {
            LOGGER.error("Something went wrong getting last fitness info information from runkeeper api. "+e.getMessage());
        }
        return rpr;
    }

    public boolean addCommentToFitnessActivity(Integer fitnessId, String msg, String accessToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/vnd.com.runkeeper.Comment+json");
        headers.put("Authorization", "Bearer "+accessToken);

        RunKeeperFitnessCommentRequest request = new RunKeeperFitnessCommentRequest();
        request.setComment(msg);
        try {
            // GET for some reason
            HttpClientUtils.execute(runKeeperApiFitnessActivitiesUrl + "/" + fitnessId + "/comments", headers, "POST", request, null);
            return true;
        } catch (Exception e ) {
            LOGGER.error("Something went wrong sending a comment to the runkeeper api. "+e.getMessage());
            return false;
        }
    }



}
