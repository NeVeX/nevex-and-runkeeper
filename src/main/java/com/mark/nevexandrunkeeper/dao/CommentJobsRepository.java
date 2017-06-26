package com.mark.nevexandrunkeeper.dao;

import com.mark.nevexandrunkeeper.dao.entity.CommentJobEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Repository
public interface CommentJobsRepository extends CrudRepository<CommentJobEntity, Integer> {

}
