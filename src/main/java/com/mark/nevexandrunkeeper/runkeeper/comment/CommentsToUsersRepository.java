package com.mark.nevexandrunkeeper.runkeeper.comment;

import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.CommentsToUsersEntity;
import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.LatestCommentForUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Repository
@Transactional
public interface CommentsToUsersRepository extends CrudRepository<CommentsToUsersEntity, Integer> {

}
