package com.mark.nevexandrunkeeper.runkeeper.oauth;

import com.mark.nevexandrunkeeper.runkeeper.oauth.model.entity.OAuthUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Repository
@Transactional
interface OAuthUsersRepository extends CrudRepository<OAuthUserEntity, Integer> {

    @Query("select u from OAuthUserEntity u where u.userId = :userId and u.isActive = true")
    List<OAuthUserEntity> findByUserIdAndIsActive(@Param("userId") int userId);

    @Query("select u from OAuthUserEntity u where u.accessToken = :accessToken and u.isActive = true")
    Optional<OAuthUserEntity> findActiveAccessToken(@Param("accessToken") String accessToken);

    @Query("select u from OAuthUserEntity u where u.oauthCode = :oauthCode and u.isActive = true")
    Optional<OAuthUserEntity> findByActiveOAuthCode(@Param("oauthCode") String oauthCode);

}
