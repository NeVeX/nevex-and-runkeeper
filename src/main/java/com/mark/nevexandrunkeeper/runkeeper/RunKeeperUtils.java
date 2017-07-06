package com.mark.nevexandrunkeeper.runkeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
public final class RunKeeperUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(RunKeeperUtils.class);

    // We expect the string in the form of Sat, 1 Jan 2011 00:00:00
    private static DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("E, d MMM u HH:mm:ss");

    public static Optional<LocalDate> parseBirthdayDate(String birthday) {
        if (!StringUtils.isEmpty(birthday)) {
            try {
                return Optional.of(LocalDate.parse(birthday, BIRTHDAY_FORMATTER));
            } catch (DateTimeParseException ex ) {
                LOGGER.warn("Could not parse given birthday [{}] into a date. Reason: [{}]", birthday, ex.getMessage());
            }
        }
        return Optional.empty();
    }

}
