package com.mark.nevexandrunkeeper.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Mark Cunningham on 6/25/2017.
 */
@Configuration
@Validated
@ConfigurationProperties(prefix = "nevex-and-runkeeper")
class LocalProperties implements ApplicationProperties{

    @NotNull
    private Integer userId;
    @Valid
    @NotNull
    private Quotation quotation;
    @Valid
    @NotNull
    private RunKeeperApi runKeeperApi;
    @Valid
    @NotNull
    private OAuth oauth;

    public static class RunKeeperApi implements ApplicationProperties.RunKeeperApi {
        @NotNull
        @NotEmpty
        private String baseUrl;
        @NotNull
        @NotEmpty
        private String userUrl;
        @NotNull
        @NotEmpty
        private String profileUrl;
        @NotNull
        @NotEmpty
        private String fitnessActivitiesUrl;

        @Override
        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        public String getUserUrl() {
            return userUrl;
        }

        public void setUserUrl(String userUrl) {
            this.userUrl = userUrl;
        }

        @Override
        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

        @Override
        public String getFitnessActivitiesUrl() {
            return fitnessActivitiesUrl;
        }

        public void setFitnessActivitiesUrl(String fitnessActivitiesUrl) {
            this.fitnessActivitiesUrl = fitnessActivitiesUrl;
        }
    }

    public static class OAuth implements ApplicationProperties.OAuth {
        @NotNull
        @NotEmpty
        private String redirectHost;
        @NotNull
        @NotEmpty
        private String redirectUrl;
        @NotNull
        @NotEmpty
        private String registerUrl;
        @NotNull
        @NotEmpty
        private String tokenUrl;
        @NotNull
        @NotEmpty
        private String baseUrl;
        @NotNull
        @NotEmpty
        private String clientId;
        @NotNull
        @NotEmpty
        private String clientSecret;
        @NotNull
        @NotEmpty
        private String code;
        @NotNull
        @NotEmpty
        private String accessToken;

        @Override
        public String getRedirectHost() {
            return redirectHost;
        }

        public void setRedirectHost(String redirectHost) {
            this.redirectHost = redirectHost;
        }

        @Override
        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        @Override
        public String getRegisterUrl() {
            return registerUrl;
        }

        public void setRegisterUrl(String registerUrl) {
            this.registerUrl = registerUrl;
        }

        @Override
        public String getTokenUrl() {
            return tokenUrl;
        }

        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        @Override
        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        @Override
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        @Override
        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    public static class Quotation implements ApplicationProperties.Quotation {
        @NotNull
        @NotEmpty
        private String forismaticUrl;

        @Override
        public String getForismaticUrl() {
            return forismaticUrl;
        }

        public void setForismaticUrl(String forismaticUrl) {
            this.forismaticUrl = forismaticUrl;
        }
    }

    @Override
    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    @Override
    public RunKeeperApi getRunkeeperApi() {
        return runKeeperApi;
    }

    public void setRunkeeperApi(RunKeeperApi runKeeperApi) {
        this.runKeeperApi = runKeeperApi;
    }

    @Override
    public OAuth getOauth() {
        return oauth;
    }

    public void setOauth(OAuth oauth) {
        this.oauth = oauth;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
