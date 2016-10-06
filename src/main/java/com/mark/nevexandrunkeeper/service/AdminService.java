package com.mark.nevexandrunkeeper.service;

import com.googlecode.objectify.ObjectifyService;
import com.mark.nevexandrunkeeper.model.QuotationResponse;
import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.model.entity.AdminCommentJobRunEntity;
import com.mark.nevexandrunkeeper.model.entity.CommentJobEntity;
import com.mark.nevexandrunkeeper.model.runkeeper.RunKeeperFitnessActivityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Service
public class AdminService {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class.getName());

    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private RunKeeperService runKeeperService;
    @Autowired
    private QuotationService quotationService;


    @Value("${application.oauth-access-token}")
    private String applicationAccessToken;

    public AdminCommentJobRunEntity runCommentsJob() {

        long commentsAdded = 0;
        long activeUsers = 0;
        long commentsFailed = 0;
        long commentsIgnored = 0;
        long startTime = System.currentTimeMillis();

        // Get all the users that are still active
        Set<User> userRegistrationList = userRegistrationService.getActiveUsers();
        if ( userRegistrationList != null && !userRegistrationList.isEmpty()) {
            activeUsers = userRegistrationList.size();
            // get the last info for this user
            List<CommentJobEntity> commentJobEntities = ObjectifyService.ofy().load().type(CommentJobEntity.class).list();

            for (User u : userRegistrationList) {


                try {
                    CommentJobEntity lastJob = null;
                    for (CommentJobEntity c : commentJobEntities) {
                        if (c.getUserId().equals(u.getUserId())) {
                            lastJob = c;
                            break;
                        }
                    }

                    if (lastJob == null) {
                        lastJob = new CommentJobEntity();
                        lastJob.setUserId(u.getUserId());
                        lastJob.setLastFitnessId(-1L);
                    }

                    RunKeeperFitnessActivityResponse activityResponse = runKeeperService.getLastFitness(u.getAccessToken());
                    if (activityResponse != null) {
                        Integer fitnessId = Integer.parseInt(activityResponse.getUri().split("/")[2]);
                        if (fitnessId > lastJob.getLastFitnessId()) {
                            // this is a new fitness job

                            QuotationResponse quoteToUse = quotationService.getQuote();
                            boolean success = runKeeperService.addCommentToFitnessActivity(fitnessId, quoteToUse.getQuote(), applicationAccessToken);
                            if (success) {
                                lastJob.setLastFitnessId(new Long(fitnessId));
                                lastJob.setUserId(u.getUserId());
                                lastJob.setLastSuccessfulRun(new Date());
                                ObjectifyService.ofy().save().entity(lastJob).now();
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
                    LOGGER.severe("Could not run job for user ["+u.getUserId()+"] with name ["+u.getName()+"]");
                    commentsFailed++;
                }

            }
        }
        long timeTaken = System.currentTimeMillis() - startTime;
        // save the admin run
        AdminCommentJobRunEntity ae = new AdminCommentJobRunEntity();
        ae.setActiveUsers(activeUsers);
        ae.setCommentsAdded(commentsAdded);
        ae.setDateRan(new Date());
        ae.setCommentsFailed(commentsFailed);
        ae.setCommentsIgnored(commentsIgnored);
        ae.setTimeTakenMs(timeTaken);
        ae.setCommentUsed("**Many quotes per user**");
//        ObjectifyService.ofy().save().entity(ae); // Don't save it anymore
        return ae;
    }

}
