package com.mark.nevexandrunkeeper.runkeeper.oauth;

import com.mark.nevexandrunkeeper.runkeeper.oauth.model.entity.OAuthUserEntity;
import com.mark.nevexandrunkeeper.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/27/2017.
 */
@Service
public class OAuthUserService {

    private final OAuthUsersRepository oAuthUsersRepository;

    @Autowired
    OAuthUserService(OAuthUsersRepository oAuthUsersRepository) {
        this.oAuthUsersRepository = oAuthUsersRepository;
    }

    public Optional<String> getAccessTokenForActiveUserId(int userId) {
        List<OAuthUserEntity> userEntities = oAuthUsersRepository.findByUserIdAndIsActive(userId);
        if ( userEntities.isEmpty()) {
            return Optional.empty();
        }
        // TODO: Fix when multiple records come back
        return Optional.ofNullable(userEntities.get(0).getAccessToken()); // just pick the first one
    }

    public boolean saveNewRegistrationCode(String oauthCode) {
        OAuthUserEntity entity = new OAuthUserEntity();
        entity.setCreatedDate(TimeUtils.utcNow());
        entity.setOauthCode(oauthCode);
        entity.setIsActive(true);
        return oAuthUsersRepository.save(entity) != null; // return if we saved or not
    }

    public boolean saveAccessTokenForOauthCode(String accessToken, String oauthCode) {
        OAuthUserEntity activeOAuth = oAuthUsersRepository.findByActiveOAuthCode(oauthCode);
        activeOAuth.setAccessToken(accessToken);
        activeOAuth.setUpdatedDate(TimeUtils.utcNow());
        return oAuthUsersRepository.save(activeOAuth) != null;
    }

    public int setAllOAuthRecordsAsInactiveForUserId(int userId) {
        List<OAuthUserEntity> activeOAuthEntries = oAuthUsersRepository.findByUserIdAndIsActive(userId);
        for ( OAuthUserEntity entity : activeOAuthEntries) {
            entity.setIsActive(false);
            entity.setUpdatedDate(TimeUtils.utcNow());
            // TODO: Throw exception if not saved...
            oAuthUsersRepository.save(entity);
        }
        return activeOAuthEntries.size();
    }

    public boolean setActiveOauthForUserAndToken(int userId, String accessToken) {
        OAuthUserEntity entity = oAuthUsersRepository.findUserWithAccessToken(userId, accessToken);
        entity.setIsActive(true);
        entity.setUpdatedDate(TimeUtils.utcNow());
        return oAuthUsersRepository.save(entity) != null;
    }


}
