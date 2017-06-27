package com.mark.nevexandrunkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Mark Cunningham on 6/25/2017.
 */
@SpringBootApplication
@EnableScheduling
public class NeVeXAndRunKeeperApplication {

    public static void main(String[] args) {
        // Let's get this baby going!
        SpringApplication.run(NeVeXAndRunKeeperApplication.class, args);
    }

}
