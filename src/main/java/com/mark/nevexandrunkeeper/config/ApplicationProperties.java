package com.mark.nevexandrunkeeper.config;

/**
 * Created by Mark Cunningham on 6/25/2017.
 */
public interface ApplicationProperties {

    Quotation getQuotation();
    RunKeeperApi getRunKeeperApi();
    OAuth getOauth();

    interface OAuth {
        String getRedirectHost();
        String getRedirectUrl();
        String getRegisterUrl();
        String getTokenUrl();
        String getBaseUrl();
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
