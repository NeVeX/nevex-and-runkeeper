package com.mark.nevexandrunkeeper.runkeeper.comment;

import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.quote.Quote;
import com.mark.nevexandrunkeeper.runkeeper.api.model.RunKeeperFitnessActivityResponse;
import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.CommentJobEntity;
import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.LatestCommentForUserEntity;
import com.mark.nevexandrunkeeper.util.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Mark Cunningham on 6/28/2017.
 */
@Service
public class CommentService {

    public void sendCommentsToFriends() {
        int commentsAdded = 0;
        int activeUsers = 0;
        int commentsFailed = 0;
        int commentsIgnored = 0;
        long startTime = System.currentTimeMillis();

        // Get all the user that are still active
        Set<User> userRegistrationList = registrationService.getActiveUsers();
        if ( userRegistrationList != null && !userRegistrationList.isEmpty()) {
            activeUsers = userRegistrationList.size();
            // get the last info for this user
            Iterable<LatestCommentForUserEntity> commentJobEntities = latestCommentForUserRepository.findAll();

            for (User u : userRegistrationList) {
                try {
                    LatestCommentForUserEntity lastJob = null;
                    for (LatestCommentForUserEntity c : commentJobEntities) {
                        if (c.getUserId() == u.getUserId()) {
                            lastJob = c;
                            break;
                        }
                    }

                    if (lastJob == null) {
                        lastJob = new LatestCommentForUserEntity();
                        lastJob.setUserId(u.getUserId());
                        lastJob.setLastFitnessId(-1L);
                    }

                    RunKeeperFitnessActivityResponse activityResponse = runKeeperService.getLastFitness(u.getAccessToken());
                    if (activityResponse != null) {
                        Integer fitnessId = Integer.parseInt(activityResponse.getUri().split("/")[2]);
                        if (fitnessId > lastJob.getLastFitnessId()) {
                            // this is a new fitness job
                            Quote quoteToUse = quotationService.getQuote();
                            boolean success = runKeeperService.addCommentToFitnessActivity(fitnessId, quoteToUse.getQuote(), applicationAccessToken);
                            if (success) {
                                lastJob.setLastFitnessId(new Long(fitnessId));
                                lastJob.setUserId(u.getUserId());
                                lastJob.setLastCommentAddedDate(TimeUtils.utcNow());
                                latestCommentForUserRepository.save(lastJob);
                                commentsAdded++;
                            } else {
                                commentsFailed++;
                            }
                        } else {
                            commentsIgnored++;
                        }
                    } else {
                        commentsFailed++;
                    }
                } catch (Exception e ) {
                    LOGGER.error("Could not run job for user ["+u.getUserId()+"] with name ["+u.getName()+"]");
                    commentsFailed++;
                }
            }
        }
        long timeTaken = System.currentTimeMillis() - startTime;
        // save the admin run
        CommentJobEntity ae = new CommentJobEntity();
        ae.setActiveUsers(activeUsers);
        ae.setCommentsAdded(commentsAdded);
        ae.setDateRan(TimeUtils.utcNow());
        ae.setCommentsFailed(commentsFailed);
        ae.setCommentsIgnored(commentsIgnored);
        ae.setTimeTakenMs(Long.valueOf(timeTaken).intValue());
        ae.setCommentUsed("**Many quotes per user**"); // TODO: Save the comment per user
        return ae;
    }

}
