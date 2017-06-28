package com.mark.nevexandrunkeeper.service;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.dao.LatestCommentForUserRepository;
import com.mark.nevexandrunkeeper.dao.entity.CommentJobEntity;
import com.mark.nevexandrunkeeper.dao.entity.LatestCommentForUserEntity;
import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.model.runkeeper.RunKeeperFitnessActivityResponse;
import com.mark.nevexandrunkeeper.quote.QuotationService;
import com.mark.nevexandrunkeeper.quote.Quote;
import com.mark.nevexandrunkeeper.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by NeVeX on 7/12/2016.
 */
@Service
public class AdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    private final UserRegistrationService userRegistrationService;
    private final RunKeeperService runKeeperService;
    private final QuotationService quotationService;
    private final LatestCommentForUserRepository latestCommentForUserRepository;
    private final String applicationAccessToken;

    @Autowired
    AdminService(ApplicationProperties applicationProperties,
                        LatestCommentForUserRepository latestCommentForUserRepository,
                        UserRegistrationService userRegistrationService,
                        RunKeeperService runKeeperService,
                        QuotationService quotationService) {
        this.applicationAccessToken = applicationProperties.getOauth().getAccessToken();
        this.latestCommentForUserRepository = latestCommentForUserRepository;
        this.userRegistrationService = userRegistrationService;
        this.runKeeperService = runKeeperService;
        this.quotationService = quotationService;
    }

    @Scheduled(initialDelay = 20000, fixedDelay = 60000) // Run every minute
    CommentJobEntity runCommentsJob() {
        LOGGER.info("Comments job kicked off");
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
