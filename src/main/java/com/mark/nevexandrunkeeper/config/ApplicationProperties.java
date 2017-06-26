package com.mark.nevexandrunkeeper.config;

/**
 * Created by Mark Cunningham on 6/25/2017.
 */
public interface ApplicationProperties {

    Quotation getQuotation();
    RunKeeperApi getRunKeeperApi();
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
        String getFitnessActivitiesUrl();
    }

    interface Quotation {
        String getForismaticUrl();
    }

}
