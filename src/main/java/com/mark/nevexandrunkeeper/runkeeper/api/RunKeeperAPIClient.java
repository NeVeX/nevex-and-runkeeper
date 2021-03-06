package com.mark.nevexandrunkeeper.runkeeper.api;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.runkeeper.api.exception.RunKeeperAPIException;
import com.mark.nevexandrunkeeper.runkeeper.api.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by NeVeX on 7/13/2016.
 */
@Component
public final class RunKeeperAPIClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunKeeperAPIClient.class);

    private final String oauthClientId;
    private final String oauthClientSecret;
    private final String oauthRedirectUrl;
    private final String runKeeperApiOauthTokenUrl;
    private final String runKeeperApiUserUrl;
    private final String runKeeperApiProfileUrl;
    private final String runKeeperApiTeamUrl;
    private final String runKeeperApiFitnessActivitiesUrl;
    private final String runKeeperApiBaseUrl;
    private final RestTemplate restTemplate;

    @Autowired
    RunKeeperAPIClient(ApplicationProperties applicationProperties) {
        this.oauthClientId = applicationProperties.getOauth().getClientId();
        this.oauthClientSecret = applicationProperties.getOauth().getClientSecret();
        this.oauthRedirectUrl = applicationProperties.getOauth().getRedirectUrl();
        this.runKeeperApiOauthTokenUrl = applicationProperties.getOauth().getTokenUrl();
        this.runKeeperApiUserUrl = applicationProperties.getRunkeeperApi().getUserUrl();
        this.runKeeperApiProfileUrl = applicationProperties.getRunkeeperApi().getProfileUrl();
        this.runKeeperApiFitnessActivitiesUrl = applicationProperties.getRunkeeperApi().getFitnessActivitiesUrl();
        this.runKeeperApiBaseUrl = applicationProperties.getRunkeeperApi().getBaseUrl();
        this.runKeeperApiTeamUrl = applicationProperties.getRunkeeperApi().getTeamUrl();
        restTemplate = new RestTemplate();
    }

    public Optional<String> getAccessTokenForCode(String oauthCode) throws RunKeeperAPIException {
        RunKeeperAccessTokenResponse tokenResponse = getAccessTokenResponseForCode(oauthCode);
        if ( tokenResponse != null && !StringUtils.isEmpty(tokenResponse.getAccessToken())) {
            return Optional.ofNullable(tokenResponse.getAccessToken());
        }
        return Optional.empty();
    }

    private RunKeeperAccessTokenResponse getAccessTokenResponseForCode(String code) throws RunKeeperAPIException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap<String, String> formData = getFormDataForAccessToken(code);

        HttpEntity httpEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<RunKeeperAccessTokenResponse> response;

        try {
            response = restTemplate.exchange(runKeeperApiOauthTokenUrl, HttpMethod.POST, httpEntity, RunKeeperAccessTokenResponse.class);
        } catch (RestClientException ex) {
            LOGGER.error("An error occurred while trying to get an access token for code [{}]. Url [{}]. FormData [{}]",
                    code, runKeeperApiOauthTokenUrl, formData);
            throw new RunKeeperAPIException("Could not get an access token for code "+code, ex);
        }

        if ( !response.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("Received a non-ok code [{}] from RunKeeper while trying to get an access token for code [{}]",
                    response.getStatusCode(), code);
            throw new RunKeeperAPIException("Could not get an access token for code "+code);
        }
        return response.getBody();
    }

    private MultiValueMap<String, String> getFormDataForAccessToken(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", oauthClientId);
        map.add("client_secret", oauthClientSecret);
        map.add("redirect_uri", oauthRedirectUrl);
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        return map;
    }

    public Optional<Integer> getUserId(String accessToken) throws RunKeeperAPIException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.com.runkeeper.User+json");
        headers.add("Authorization", "Bearer "+accessToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RunKeeperUserResponse> userResponse;
        try {
            userResponse = restTemplate.exchange(runKeeperApiUserUrl, HttpMethod.GET, httpEntity, RunKeeperUserResponse.class);
        } catch (RestClientException ex) {
            LOGGER.error("An error occurred while trying to get the userId for access token [{}]. Url [{}]. Headers [{}]",
                    accessToken, runKeeperApiUserUrl, headers);
            throw new RunKeeperAPIException("Could not get the userId for access token ["+accessToken+"]", ex);
        }

        if ( !userResponse.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("Received a non-ok code [{}] from RunKeeper while trying to get the userId for access token [{}]",
                    userResponse.getStatusCode(), accessToken);
            throw new RunKeeperAPIException("Could not get the userId for access token ["+accessToken+"]");
        }
        return Optional.ofNullable(userResponse.getBody().getUserId());
    }

    public Optional<RunKeeperProfileResponse> getProfileInformation(String accessToken) throws RunKeeperAPIException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/vnd.com.runkeeper.Profile+json");
        headers.add("Authorization", "Bearer "+accessToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<RunKeeperProfileResponse> profileResponse;
        try {
            profileResponse = restTemplate.exchange(runKeeperApiProfileUrl, HttpMethod.GET, httpEntity, RunKeeperProfileResponse.class);
        } catch (Exception ex ) {
            LOGGER.error("An error occurred while trying to get the user profile for access token [{}]. Url [{}]. Headers [{}]",
                    accessToken, runKeeperApiProfileUrl, headers);
            throw new RunKeeperAPIException("Could not get the user profile for access token ["+accessToken+"]", ex);
        }

        if ( !profileResponse.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("Received a non-ok code [{}] from RunKeeper while trying to get the user profile for access token [{}]",
                    profileResponse.getStatusCode(), accessToken);
            throw new RunKeeperAPIException("Could not get the user profile for access token ["+accessToken+"]");
        }
        return Optional.ofNullable(profileResponse.getBody());
    }

    public boolean sendFriendRequest(String accessToken, int userIdToFriend) throws RunKeeperAPIException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/vnd.com.runkeeper.Invitation+json");
        headers.add("Authorization", "Bearer "+accessToken);

        RunKeeperFriendInvitationRequest invitationRequest = new RunKeeperFriendInvitationRequest();
        invitationRequest.setUserId(userIdToFriend);

        HttpEntity httpEntity = new HttpEntity<>(invitationRequest, headers);
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(runKeeperApiTeamUrl, HttpMethod.POST, httpEntity, String.class);
        } catch (Exception ex ) {
            LOGGER.error("An error occurred while trying to send a friend request for access token [{}] and userId [{}]. Url [{}]. Headers [{}]",
                    accessToken, userIdToFriend, runKeeperApiTeamUrl, headers);
            throw new RunKeeperAPIException("Could not send fried request for access token ["+accessToken+"]", ex);
        }

        if ( !response.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("Received a non-successful code [{}] from RunKeeper while trying send a friend request for access token [{}]",
                    response.getStatusCode(), accessToken);
            throw new RunKeeperAPIException("Could not send friend request for access token ["+accessToken+"]");
        }

        return true;
    }

    public Optional<RunKeeperFitnessActivityResponse> getLatestFitness(String accessToken) throws RunKeeperAPIException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/vnd.com.runkeeper.FitnessActivity+json");
        headers.add("Accept", "*/*"); // RunKeeper is broken without this....
        headers.add("Authorization", "Bearer "+accessToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(runKeeperApiFitnessActivitiesUrl)
                .queryParam("pageSize", 1).build().encode().toUri();

        ResponseEntity<RunKeeperFitnessActivityListResponse> response;

        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, RunKeeperFitnessActivityListResponse.class);
        } catch (Exception ex ) {
            LOGGER.error("An error occurred while trying to get the latest fitness for access token [{}]. Url [{}]. Headers [{}]",
                    accessToken, uri, headers);
            throw new RunKeeperAPIException("Could not get latest fitness activity for access token ["+accessToken+"]", ex);
        }

        if ( !response.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("Received a non-ok code [{}] from RunKeeper while trying to get latest fitness activity for access token [{}]",
                    response.getStatusCode(), accessToken);
            throw new RunKeeperAPIException("Could not get latest fitness activity for access token ["+accessToken+"]");
        }

        if ( response.getBody() != null && response.getBody().getItems() != null && !response.getBody().getItems().isEmpty()) {
            return Optional.ofNullable(response.getBody().getItems().get(0));
        }
        return Optional.empty();
    }

    public Set<Integer> getAllFriends(String accessToken) throws RunKeeperAPIException {
        Set<Integer> allFriends = new HashSet<>();

        String pageUri = "/team";
        boolean hasMoreFriends = true;

        while ( hasMoreFriends) {
            RunKeeperFriendsReplyWrapperResponse response = getAllFriendsInPage(accessToken, pageUri);
            // Check if we have friends
            List<RunKeeperFriendsReplyResponse> friends = response.getItems();
            if (friends != null && !friends.isEmpty()) {
                // We have some friends
                allFriends.addAll(friends.stream().map(RunKeeperFriendsReplyResponse::getUserId).collect(Collectors.toList()));
                // Do we have more friends? Check the next uri?
                hasMoreFriends = StringUtils.hasText(response.getNextUri());
                if ( hasMoreFriends ) {
                    // Yup, we have more friends to get
                    pageUri = response.getNextUri(); // Set the page uri to get the next set of friends
                }
            } else {
                hasMoreFriends = false; // no more friends
            }
        }

        return allFriends;
    }

    private RunKeeperFriendsReplyWrapperResponse getAllFriendsInPage(String accessToken, String pageUri) throws RunKeeperAPIException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/vnd.com.runkeeper.Reply+json");
        headers.add("Accept", "*/*"); // RunKeeper is broken without this....
        headers.add("Authorization", "Bearer "+accessToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);


        ResponseEntity<RunKeeperFriendsReplyWrapperResponse> response;
        String url = runKeeperApiBaseUrl + pageUri; // need to build it here since we'll use the API url for pagination

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, RunKeeperFriendsReplyWrapperResponse.class);
        } catch (RestClientException ex) {
            LOGGER.error("An error occurred while trying to get all friends for access token [{}]. Url [{}]. Headers [{}]",
                    accessToken, url, headers);
            throw new RunKeeperAPIException("Could not get friends for access token ["+accessToken+"]", ex);
        }

        if ( !response.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("Received a non-ok code [{}] from RunKeeper while trying to get all friends for access token [{}]",
                    response.getStatusCode(), accessToken);
            throw new RunKeeperAPIException("Could not get all friends for access token ["+accessToken+"]");
        }
        return response.getBody();
    }


    public boolean addCommentToFitnessActivity(Long fitnessId, String msg, String accessToken) throws RunKeeperAPIException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/vnd.com.runkeeper.Comment+json");
        headers.add("Accept", "*/*"); // RunKeeper is broken without this....
        headers.add("Authorization", "Bearer "+accessToken);

        RunKeeperFitnessCommentRequest request = new RunKeeperFitnessCommentRequest();
        request.setComment(msg);

        HttpEntity httpEntity = new HttpEntity<>(request, headers);
        String url = runKeeperApiFitnessActivitiesUrl + "/" + fitnessId + "/comments";

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        } catch (Exception ex ) {
            LOGGER.error("An error occurred while trying to add a comment to fitness id [{}] with access token [{}]. Url [{}]. Headers [{}]",
                    fitnessId, accessToken, url, headers);
            throw new RunKeeperAPIException("Could not add comment to fitness id ["+fitnessId+"] for access token ["+accessToken+"]", ex);
        }

        if ( !response.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("Received a non-ok code [{}] from RunKeeper while trying to add a comment to fitness activity [{}] with access token [{}]",
                    response.getStatusCode(), fitnessId, accessToken);
            throw new RunKeeperAPIException("Could not add a comment to fitness id ["+fitnessId+"] activity with access token ["+accessToken+"]");
        }
        return true;
    }

}
