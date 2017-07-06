package com.mark.nevexandrunkeeper.util;

import com.mark.nevexandrunkeeper.runkeeper.RunKeeperUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
public class RunKeeperUtilsTest {

    @Test
    public void testBirthdayCanParse() {
        // This is what model will give, so make sure it parses correctly
        String birthday = "Sat, 1 Jan 2011 00:00:00";
        Optional<LocalDate> parsedDateOptional = RunKeeperUtils.parseBirthdayDate(birthday);
        assertTrue(parsedDateOptional.isPresent());
        LocalDate localBirthday = parsedDateOptional.get();
        assertTrue(localBirthday.getMonth() == Month.JANUARY);
        assertTrue(localBirthday.getDayOfMonth() == 1);
        assertTrue(localBirthday.getYear() == 2011);
    }

}
