package com.mark.nevexandrunkeeper.service;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.dao.LatestCommentForUserRepository;
import com.mark.nevexandrunkeeper.dao.entity.CommentJobEntity;
import com.mark.nevexandrunkeeper.dao.entity.LatestCommentForUserEntity;
import com.mark.nevexandrunkeeper.model.QuotationResponse;
import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.model.runkeeper.RunKeeperFitnessActivityResponse;
import com.mark.nevexandrunkeeper.service.quotes.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Set;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Service
public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class.getName());

    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private RunKeeperService runKeeperService;
    @Autowired
    private QuotationService quotationService;
    private final LatestCommentForUserRepository latestCommentForUserRepository;
    private final String applicationAccessToken;

    @Autowired
    public AdminService(ApplicationProperties applicationProperties, LatestCommentForUserRepository latestCommentForUserRepository) {
        this.applicationAccessToken = applicationProperties.getOauth().getAccessToken();
        this.latestCommentForUserRepository = latestCommentForUserRepository;
    }

    @PostConstruct
    void init() {
        LatestCommentForUserEntity entity = new LatestCommentForUserEntity();
        entity.setLastFitnessId(1234L);
        entity.setLastSuccessfulRun(new Date());
        entity.setUserId(111);
        latestCommentForUserRepository.save(entity);

    }

    public CommentJobEntity runCommentsJob() {
        int commentsAdded = 0;
        int activeUsers = 0;
        int commentsFailed = 0;
        int commentsIgnored = 0;
        long startTime = System.currentTimeMillis();

        // Get all the users that are still active
        Set<User> userRegistrationList = userRegistrationService.getActiveUsers();
        if ( userRegistrationList != null && !userRegistrationList.isEmpty()) {
            activeUsers = userRegistrationList.size();
            // get the last info for this user
            Iterable<LatestCommentForUserEntity> commentJobEntities = latestCommentForUserRepository.findAll();
                    // ObjectifyService.ofy().load().type(LatestCommentForUserEntity.class).list();

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

                            QuotationResponse quoteToUse = quotationService.getQuote();
                            boolean success = runKeeperService.addCommentToFitnessActivity(fitnessId, quoteToUse.getQuote(), applicationAccessToken);
                            if (success) {
                                lastJob.setLastFitnessId(new Long(fitnessId));
                                lastJob.setUserId(u.getUserId());
                                lastJob.setLastSuccessfulRun(new Date());
//                                ObjectifyService.ofy().save().entity(lastJob).now();

                                latestCommentForUserRepository.save(lastJob); // new way


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
        ae.setDateRan(new Date());
        ae.setCommentsFailed(commentsFailed);
        ae.setCommentsIgnored(commentsIgnored);
        ae.setTimeTakenMs(Long.valueOf(timeTaken).intValue());
        ae.setCommentUsed("**Many quotes per user**"); // TODO: Save the comment per user
        return ae;
    }

}
