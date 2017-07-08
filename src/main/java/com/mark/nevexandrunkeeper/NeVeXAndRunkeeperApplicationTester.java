package com.mark.nevexandrunkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Mark Cunningham on 6/25/2017.
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class NeVeXAndRunkeeperApplicationTester {

    public static void main(String[] args) {
        // Let's get this baby going!
        SpringApplication.run(NeVeXAndRunkeeperApplicationTester.class, args);
    }

}
