package com.mark.nevexandrunkeeper.runkeeper.oauth;

import com.mark.nevexandrunkeeper.runkeeper.oauth.model.entity.OAuthUserEntity;
import com.mark.nevexandrunkeeper.util.TimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mcunningham on 7/5/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-testing.yml")
@EnableTransactionManagement
public class OAuthUsersIntegrationTest {

    @Autowired
    private OAuthUsersRepository oAuthUsersRepository;

    @Test
    @Rollback
    @Transactional
    public void makeSureWeCanSaveAndRetrieve() {
        String testCode = UUID.randomUUID().toString();
        Optional<OAuthUserEntity> optional = oAuthUsersRepository.findByActiveOAuthCode(testCode);
        assertThat(optional.isPresent()).isFalse(); // it should not exist

        // now save it
        OAuthUserEntity newEntry = new OAuthUserEntity();
        newEntry.setUserId(666);
        newEntry.setIsActive(true);
        newEntry.setCreatedDate(TimeUtils.utcNow());
        newEntry.setOauthCode(testCode);
        assertThat(oAuthUsersRepository.save(newEntry)).isNotNull();

        optional = oAuthUsersRepository.findByActiveOAuthCode(testCode);
        assertThat(optional.isPresent()).isTrue(); // it should now exist

    }

}
