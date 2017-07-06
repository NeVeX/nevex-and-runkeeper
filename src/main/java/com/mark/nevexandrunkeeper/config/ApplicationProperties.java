package com.mark.nevexandrunkeeper.config;

/**
 * Created by Mark Cunningham on 6/25/2017.
 */
public interface ApplicationProperties {

    Quotation getQuotation();
    RunKeeperApi getRunkeeperApi();
    OAuth getOauth();
    Integer getUserId();

    interface OAuth {
        String getRedirectHost();
        String getRedirectUrl();
        String getRegisterUrl();
        String getTokenUrl();
        String getBaseUrl();
        String getClientId();
        String getClientSecret();
        String getCode();
        String getAccessToken();
    }

    interface RunKeeperApi {
        String getBaseUrl();
        String getUserUrl();
        String getProfileUrl();
        String getTeamUrl();
        String getFitnessActivitiesUrl();
    }

    interface Quotation {
        String getForismaticUrl();
    }

    /**
     * Cannot name this "toString" since it will be override by Object.toString();
     */
    default String toStringProperties() {
        String props = "UserId: "+getUserId()+".";
        if ( getOauth() != null ) {
            props += " Oauth { " +
                    "redirectHost: " + getOauth().getRedirectHost() +
                    ", redirectUrl: " + getOauth().getRedirectUrl() +
                    ", registerUrl: " + getOauth().getRegisterUrl() +
                    ", tokenUrl: " + getOauth().getTokenUrl() +
                    ", baseUrl: " + getOauth().getBaseUrl() +
                    ", clientId: " + getOauth().getClientId() +
                    ", clientSecret: " + getOauth().getClientSecret() +
                    ", code: " + getOauth().getCode() +
                    ", accessToken: " + getOauth().getAccessToken() +
                    "}.";
        }
        if ( getRunkeeperApi() != null ) {
            props += " RunKeeperApi { " +
                    "baseUrl: " + getRunkeeperApi().getBaseUrl() +
                    ", userUrl: " + getRunkeeperApi().getUserUrl() +
                    ", profileUrl: " + getRunkeeperApi().getProfileUrl() +
                    ", teamUrl: " + getRunkeeperApi().getTeamUrl() +
                    ", fitnessActivitiesUrl: " + getRunkeeperApi().getFitnessActivitiesUrl() +
                    "}.";
        }
        if ( getQuotation() != null ) {
            props += " Quotation { " +
                    "forismaticUrl: " + getQuotation().getForismaticUrl() +
                    "}.";
        }
        return props;
    }

}
