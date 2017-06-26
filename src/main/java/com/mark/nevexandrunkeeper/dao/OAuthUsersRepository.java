package com.mark.nevexandrunkeeper.dao;

import com.mark.nevexandrunkeeper.dao.entity.OAuthUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Repository
public interface OAuthUsersRepository extends CrudRepository<OAuthUserEntity, Integer> {

    List<OAuthUserEntity> findByUserId(@Param("userId") int userId);

    @Query("select u from OAuthUserEntity u where u.oauthCode = :oauthCode")
    List<OAuthUserEntity> findByOAuthCode(@Param("oauthCode") String oauthCode);

}
