package com.mark.nevexandrunkeeper.runkeeper.comment;

import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.LatestCommentForUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Repository
public interface LatestCommentForUserRepository extends CrudRepository<LatestCommentForUserEntity, Integer> {

}
