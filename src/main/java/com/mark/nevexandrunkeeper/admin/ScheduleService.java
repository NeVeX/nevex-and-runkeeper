package com.mark.nevexandrunkeeper.admin;

import com.mark.nevexandrunkeeper.runkeeper.comment.CommentService;
import com.mark.nevexandrunkeeper.runkeeper.exception.RunKeeperException;
import com.mark.nevexandrunkeeper.runkeeper.friend.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Created by NeVeX on 7/12/2016.
 */
@Component
class ScheduleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);
    private final FriendService friendService;
    private final CommentService commentService;

    @Autowired
    ScheduleService(FriendService friendService, CommentService commentService) {
        this.friendService = friendService;
        this.commentService = commentService;
        LOGGER.info("Scheduling service activated");
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 600000) // Delay for 1 minute, then every 10 minutes
    void sendRequestsToBeFriends() {
        LOGGER.info("Scheduling job sendRequestsToBeFriends started");
        int totalRequestsSent = this.friendService.sendAllFriendRequests();
        LOGGER.info("Scheduling job sendRequestsToBeFriends ended - total requests sent [{}]", totalRequestsSent);
    }

    @Scheduled(initialDelay = 120000, fixedDelay = 660000) // Delay for 2 minutes, then every 11 minutes
    void checkFriendsRequestAccepted() {
        LOGGER.info("Scheduling job checkFriendsRequestAccepted started");
        try {
            int newFriends = friendService.checkIfNewFriendsHaveBeenMade();
            LOGGER.info("Scheduling job checkFriendsRequestAccepted ended - total new friends made [{}]", newFriends);
        } catch (RunKeeperException ex) {
            LOGGER.error("An exception occurred while running the job checkFriendsRequestAccepted", ex);
        }

    }

    @Scheduled(initialDelay = 30000, fixedDelay = 120000) // Delay for 30 seconds, then every 2 minutes
    void sendCommentsToFriendsForLatestFitness() {
        LOGGER.info("Scheduling job sendCommentsToFriendsForLatestFitness started");
        int commentsAdded = commentService.sendCommentsToFriends();
        LOGGER.info("Scheduling job sendCommentsToFriendsForLatestFitness ended - total comments added [{}]", commentsAdded);
    }

}
