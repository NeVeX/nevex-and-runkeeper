package com.mark.nevexandrunkeeper.runkeeper.comment;

import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.CommentJobEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Repository
@Transactional
public interface CommentJobsRepository extends CrudRepository<CommentJobEntity, Integer> {

}
