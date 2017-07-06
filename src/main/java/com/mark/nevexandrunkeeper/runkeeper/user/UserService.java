package com.mark.nevexandrunkeeper.runkeeper.user;

import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.runkeeper.RunKeeperUtils;
import com.mark.nevexandrunkeeper.runkeeper.api.model.RunKeeperProfileResponse;
import com.mark.nevexandrunkeeper.runkeeper.exception.RunKeeperException;
import com.mark.nevexandrunkeeper.runkeeper.user.model.entity.RunKeeperUserEntity;
import com.mark.nevexandrunkeeper.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Mark Cunningham on 6/27/2017.
 */
@Service
@Transactional
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UsersRepository usersRepository;

    @Autowired
    UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User saveProfile(int userId, RunKeeperProfileResponse profile) throws RunKeeperException {
        RunKeeperUserEntity userEntity;
        // See if we have this user profile already
        Optional<RunKeeperUserEntity> existingUserOptional = usersRepository.findByUserId(userId);
        if ( existingUserOptional.isPresent() ) {
            LOGGER.info("Found existing user record for userId [{}], will use that to save the profile", userId);
            userEntity = existingUserOptional.get(); // set it to the existing record
        } else {
            LOGGER.info("Creating new user profile records userId [{}]", userId);
            userEntity = new RunKeeperUserEntity();
        }

        userEntity.setAthleteType(profile.getAthleteType());
        userEntity.setUserId(userId);
        userEntity.setCreatedDate(TimeUtils.utcNow());

        Optional<Date> birthDateOptional = RunKeeperUtils.parseBirthdayDate(profile.getBirthday());
        if ( birthDateOptional.isPresent() ) {
            userEntity.setBirthday(new java.sql.Date(birthDateOptional.get().getTime()));
        }

        userEntity.setGender(profile.getGender());
        userEntity.setLocation(profile.getLocation());
        userEntity.setName(profile.getName());
        userEntity.setIsActive(true);

        if (usersRepository.save(userEntity) == null) {
            throw new RunKeeperException("Could not save user profile for userId ["+userId+"]");
        }
        return new User(userId, userEntity.getName(), userEntity.getIsActive()); // Our new user in the system
    }

    // TODO: Paginate this
    public Set<User> getAllActiveUsers() {
        Set<User> users = new HashSet<>();
        for (RunKeeperUserEntity user : usersRepository.findAll()) {
            users.add(new User(
                    user.getUserId(),
                    user.getName(),
                    user.getIsActive()
            ));
        }
        return users;
    }

    public boolean setUserInactive(int userId) {
        Optional<RunKeeperUserEntity> entityOptional = usersRepository.findByUserId(userId);
        if ( entityOptional.isPresent()) {
            RunKeeperUserEntity entity = entityOptional.get();
            entity.setIsActive(false);
            entity.setUpdatedDate(TimeUtils.utcNow());
            return usersRepository.save(entity) != null;
        }
        return false; // nothing to change
    }

    public List<User> getAllActiveUsersThatHaveNotGotFriendRequested() {
        List<User> activeUsersNotFriends = new ArrayList<>();
        List<RunKeeperUserEntity> userEntities = usersRepository.findAllActiveUsersNotFriendRequested();
        for ( RunKeeperUserEntity userEntity: userEntities ) {
            activeUsersNotFriends.add(new User(userEntity.getUserId(), userEntity.getName(), userEntity.getIsActive()));
        }
        return activeUsersNotFriends;
    }

    public boolean setFriendRequestSent(int userId) {
        Optional<RunKeeperUserEntity> userEntityOptional = usersRepository.findByUserId(userId);
        if ( !userEntityOptional.isPresent()) {
            return false;
        }
        RunKeeperUserEntity entity = userEntityOptional.get();
        entity.setIsFriendRequestSent(true);
        entity.setUpdatedDate(TimeUtils.utcNow());
        return usersRepository.save(entity) != null;
    }

    public boolean setIsFriend(int userId) {
        Optional<RunKeeperUserEntity> userEntityOptional = usersRepository.findByUserId(userId);
        if ( !userEntityOptional.isPresent()) {
            return false;
        }
        RunKeeperUserEntity entity = userEntityOptional.get();
        entity.setIsFriend(true);
        entity.setUpdatedDate(TimeUtils.utcNow());
        return usersRepository.save(entity) != null;
    }

    public List<User> getAllActiveUsersThatWereFriendRequestedButAreNotFriendsYet() {
        List<RunKeeperUserEntity> notFriendsEntities = usersRepository.findAllActiveUsersNotFriendsYet();
        List<User> usersThatAreNotFriends = new ArrayList<>();
        if ( notFriendsEntities.isEmpty()) {
            return usersThatAreNotFriends;
        }

        for ( RunKeeperUserEntity userEntity : notFriendsEntities) {
            usersThatAreNotFriends.add(new User(
                    userEntity.getUserId(),
                    userEntity.getName(),
                    userEntity.getIsActive()
            ));
        }
        return usersThatAreNotFriends;
    }

}
