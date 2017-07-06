package com.mark.nevexandrunkeeper.runkeeper.user;

import com.mark.nevexandrunkeeper.runkeeper.user.model.entity.RunKeeperUserEntity;
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
interface UsersRepository extends CrudRepository<RunKeeperUserEntity, Integer> {

    @Query("select u from RunKeeperUserEntity u where u.userId = :userId")
    Optional<RunKeeperUserEntity> findByUserId(@Param("userId") int userId);

    @Query("select u from RunKeeperUserEntity u where u.isActive = true and u.isFriendRequestSent = false")
    List<RunKeeperUserEntity> findAllActiveUsersNotFriendRequested();

    @Query("select u from RunKeeperUserEntity u where u.isActive = true and u.isFriendRequestSent = true and u.isFriend = false")
    List<RunKeeperUserEntity> findAllActiveUsersNotFriendsYet();

}
