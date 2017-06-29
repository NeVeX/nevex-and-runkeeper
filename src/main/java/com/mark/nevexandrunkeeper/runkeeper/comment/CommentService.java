package com.mark.nevexandrunkeeper.runkeeper.comment;

import com.mark.nevexandrunkeeper.model.User;
import com.mark.nevexandrunkeeper.quote.QuotationService;
import com.mark.nevexandrunkeeper.quote.Quote;
import com.mark.nevexandrunkeeper.runkeeper.api.RunKeeperAPIClient;
import com.mark.nevexandrunkeeper.runkeeper.api.exception.RunKeeperAPIException;
import com.mark.nevexandrunkeeper.runkeeper.api.model.RunKeeperFitnessActivityResponse;
import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.CommentJobEntity;
import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.CommentsToUsersEntity;
import com.mark.nevexandrunkeeper.runkeeper.comment.model.entity.LatestCommentForUserEntity;
import com.mark.nevexandrunkeeper.runkeeper.oauth.OAuthUserService;
import com.mark.nevexandrunkeeper.runkeeper.user.UserService;
import com.mark.nevexandrunkeeper.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Created by Mark Cunningham on 6/28/2017.
 */
@Service
public class CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final UserService userService;
    private final OAuthUserService oAuthUserService;
    private final LatestCommentForUserRepository latestCommentForUserRepository;
    private final CommentsToUsersRepository commentsToUsersRepository;
    private final CommentJobsRepository commentJobsRepository;
    private final RunKeeperAPIClient apiClient;
    private final QuotationService quotationService;

    @Autowired
    CommentService(UserService userService,
                   OAuthUserService oAuthUserService,
                   LatestCommentForUserRepository latestCommentForUserRepository,
                   CommentsToUsersRepository commentsToUsersRepository,
                   CommentJobsRepository commentJobsRepository,
                   QuotationService quotationService,
                   RunKeeperAPIClient runKeeperAPIClient) {
        this.userService = userService;
        this.latestCommentForUserRepository = latestCommentForUserRepository;
        this.apiClient = runKeeperAPIClient;
        this.oAuthUserService = oAuthUserService;
        this.quotationService = quotationService;
        this.commentsToUsersRepository = commentsToUsersRepository;
        this.commentJobsRepository = commentJobsRepository;

    }

    public int sendCommentsToFriends() {
        int commentsAdded = 0;
        int commentsFailed = 0;
        int commentsIgnored = 0;
        long startTime = System.currentTimeMillis();

        // Get all the user that are still active
        Set<User> activeUsers = userService.getAllActiveUsers();
        int activeUsersCount = activeUsers.size();

        if ( !activeUsers.isEmpty()) {

            for (User user : activeUsers) {
                // Get the latest comment
                Optional<LatestCommentForUserEntity> latestCommentOptional = latestCommentForUserRepository.findLatestComment(user.getUserId());
                LatestCommentForUserEntity latestComment;
                if (latestCommentOptional.isPresent()) {
                    latestComment = latestCommentOptional.get();
                } else {
                    // We don't have a latest comment, so we need to create such an entity
                    latestComment = createNewLatestCommentEntity(user);
                }

                Optional<String> accessTokenOptional = oAuthUserService.getAccessTokenForActiveUserId(user.getUserId());
                if (!accessTokenOptional.isPresent()) {
                    LOGGER.warn("Could not get access token for userId [{}] - will keep trying other users", user.getUserId());
                    commentsFailed++;
                    continue; // skip this loop
                }

                // Now get the last fitness activity for this user
                String userAccessToken = accessTokenOptional.get();
                Optional<Long> lastFitnessIdOptional = getLastFitnessId(userAccessToken);
                if (!lastFitnessIdOptional.isPresent()) {
                    commentsFailed++;
                    continue;
                }

                long lastFitnessId = lastFitnessIdOptional.get();
                if (lastFitnessId > latestComment.getLastFitnessId()) {
                    // This user has done a fitness thing since the last time we checked, so we can add a new comment to their workout
                    Quote quoteToUse = quotationService.getQuote();
                    if (addCommentToFitnessActivity(lastFitnessId, quoteToUse.getQuote(), userAccessToken)) {
                        commentsAdded++;
                        saveCommentAdded(user.getUserId(), quoteToUse.getQuote());

                        latestComment.setLastCommentAddedDate(TimeUtils.utcNow());
                        latestComment.setLastFitnessId(lastFitnessId); // set to the new fitness
                        if ( latestCommentForUserRepository.save(latestComment) == null ) {
                            LOGGER.warn("Could not update the latest comment added for user [{}] with comment [{}] - this user " +
                                    "will probably get multiple comments on the same activity", user.getUserId(), quoteToUse.getQuote());
                        }
                    } else {
                        commentsFailed++;
                    }
                } else {
                    commentsIgnored++;
                }
            }
        }

        long timeTaken = System.currentTimeMillis() - startTime;
        // save the admin run
        CommentJobEntity jobEntity = new CommentJobEntity();
        jobEntity.setActiveUsers(activeUsersCount);
        jobEntity.setCommentsAdded(commentsAdded);
        jobEntity.setDateRan(TimeUtils.utcNow());
        jobEntity.setCommentsFailed(commentsFailed);
        jobEntity.setCommentsIgnored(commentsIgnored);
        jobEntity.setTimeTakenMs(Long.valueOf(timeTaken).intValue());
        if ( commentJobsRepository.save(jobEntity) == null ) {
            LOGGER.warn("Could not save this job's run details into the database. Run details were [{}]", jobEntity);
        }
        return commentsAdded;
    }

    private void saveCommentAdded(int userId, String commentUsed) {
        CommentsToUsersEntity newCommentToUser = new CommentsToUsersEntity();
        newCommentToUser.setUserId(userId);
        newCommentToUser.setComment(commentUsed);
        newCommentToUser.setCreatedDate(TimeUtils.utcNow());
        if ( commentsToUsersRepository.save(newCommentToUser) == null) {
            LOGGER.warn("Could not save new comment record for user Id [{}] with comment [{}]", userId, commentUsed);
        }
    }

    private boolean addCommentToFitnessActivity(long lastFitnessId, String quote, String userAccessToken) {
        try {
            return apiClient.addCommentToFitnessActivity(lastFitnessId, quote, userAccessToken);
        } catch (RunKeeperAPIException ex) {
            LOGGER.warn("Could not add comment for fitnessId [{}] with quote [{}] for user access token [{}]",
                    lastFitnessId, quote, userAccessToken, ex);
            return false;
        }
    }

    private Optional<Long> getLastFitnessId(String accessToken) {
        Optional<RunKeeperFitnessActivityResponse> responseOptional;
        try {
            responseOptional = apiClient.getLatestFitness(accessToken);
        } catch (RunKeeperAPIException apiEx) {
            LOGGER.warn("Could not get the latest fitness activity for access token [{}]", accessToken);
            responseOptional = Optional.empty();
        }

        if ( responseOptional.isPresent()) {
            RunKeeperFitnessActivityResponse activity = responseOptional.get();
            String fitnessUri = activity.getUri();
            Long fitnessId = null;
            try {
                fitnessId = Long.parseLong(fitnessUri.split("/")[2]);
            } catch (NumberFormatException nfe) {
                LOGGER.warn("Could not extract fitnessId from uri [{}]", fitnessUri, nfe);
            }
            return Optional.ofNullable(fitnessId);
        }

        return Optional.empty();
    }

    private LatestCommentForUserEntity createNewLatestCommentEntity(User user) {
        LatestCommentForUserEntity comment = new LatestCommentForUserEntity();
        comment.setUserId(user.getUserId());
        comment.setLastFitnessId(-1L);
        return comment;
    }

}
