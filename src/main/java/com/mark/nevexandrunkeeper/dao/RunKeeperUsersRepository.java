package com.mark.nevexandrunkeeper.dao;

import com.mark.nevexandrunkeeper.dao.entity.RunKeeperUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Repository
public interface RunKeeperUsersRepository extends CrudRepository<RunKeeperUserEntity, Integer> {

    @Query("select u from RunKeeperUserEntity u where u.userId = :userId")
    Optional<RunKeeperUserEntity> findByUserId(@Param("userId") int userId);


}
