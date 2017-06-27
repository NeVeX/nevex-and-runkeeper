package com.mark.nevexandrunkeeper.util;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
public class RunKeeperUtilsTest {

    @Test
    public void testBirthdayCanParse() {
        // This is what runkeeper will give, so make sure it parses correctly
        String birthday = "Sat, 1 Jan 2011 00:00:00";
        Optional<Date> parsedDate = RunKeeperUtils.parseBirthdayDate(birthday);
        assertTrue(parsedDate.isPresent());
        LocalDate localBirthday = parsedDate.get().toInstant().atOffset(ZoneOffset.UTC).toLocalDate();
        assertTrue(localBirthday.getMonth() == Month.JANUARY);
        assertTrue(localBirthday.getDayOfMonth() == 1);
        assertTrue(localBirthday.getYear() == 2011);
    }

}
