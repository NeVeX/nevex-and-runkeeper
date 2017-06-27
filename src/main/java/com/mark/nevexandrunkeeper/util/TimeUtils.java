package com.mark.nevexandrunkeeper.util;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
public final class TimeUtils {

    public static Timestamp utcNow() {
        return Timestamp.from(Instant.now(Clock.systemUTC()));
    }
}
