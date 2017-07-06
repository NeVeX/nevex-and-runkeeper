package com.mark.nevexandrunkeeper.runkeeper.oauth;

import com.mark.nevexandrunkeeper.runkeeper.oauth.model.entity.OAuthUserEntity;
import com.mark.nevexandrunkeeper.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/27/2017.
 */
@Service
@Transactional
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

    public boolean saveNewRegistration(String accessToken, String oauthCode) {
        OAuthUserEntity entity = new OAuthUserEntity();
        entity.setCreatedDate(TimeUtils.utcNow());
        entity.setOauthCode(oauthCode);
        entity.setAccessToken(accessToken);
        entity.setIsActive(true);
        return oAuthUsersRepository.save(entity) != null; // return if we saved or not
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

    public boolean setUserIdForToken(int userId, String accessToken) {
        Optional<OAuthUserEntity> activeAccessTokenOptional = oAuthUsersRepository.findActiveAccessToken(accessToken);
        if ( !activeAccessTokenOptional.isPresent()) {
            return false;
        }
        OAuthUserEntity activeToken = activeAccessTokenOptional.get();
        activeToken.setUserId(userId);
        activeToken.setUpdatedDate(TimeUtils.utcNow());
        return oAuthUsersRepository.save(activeToken) != null;
    }

    // TODO: Expect a list of active codes?
    public void setAllOAuthCodesToInactive(String oauthCode) {
        Optional<OAuthUserEntity> userEntityOptional = oAuthUsersRepository.findByActiveOAuthCode(oauthCode);
        if ( userEntityOptional.isPresent()) {
            OAuthUserEntity entity = userEntityOptional.get();
            entity.setIsActive(false);
            entity.setUpdatedDate(TimeUtils.utcNow());
            oAuthUsersRepository.save(entity);
        }
    }
}
