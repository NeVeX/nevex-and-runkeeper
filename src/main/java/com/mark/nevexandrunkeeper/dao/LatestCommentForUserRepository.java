package com.mark.nevexandrunkeeper.dao;

import com.mark.nevexandrunkeeper.dao.entity.LatestCommentForUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Repository
public interface LatestCommentForUserRepository extends CrudRepository<LatestCommentForUserEntity, Integer> {

}
