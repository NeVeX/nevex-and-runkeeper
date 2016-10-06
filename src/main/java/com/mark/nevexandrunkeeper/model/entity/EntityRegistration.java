package com.mark.nevexandrunkeeper.model.entity;

import com.googlecode.objectify.ObjectifyService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by NeVeX on 7/4/2016.
 */
@Component
public class EntityRegistration {

    @PostConstruct
    public void registerEntities() {
        ObjectifyService.factory().register(OAuthUserEntity.class);
        ObjectifyService.factory().register(UserEntity.class);
        ObjectifyService.factory().register(CommentJobEntity.class);
        ObjectifyService.factory().register(AdminCommentJobRunEntity.class);
    }
}
