package com.mark.nevexandrunkeeper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.*;
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

    public static Optional<Date> parseBirthdayDate(String birthday) {
        Date date = null;
        if (!StringUtils.isEmpty(birthday)) {
            try {
                LocalDate parsedDate = LocalDate.parse(birthday, BIRTHDAY_FORMATTER);
                date = Date.from(parsedDate.atStartOfDay(ZoneId.of("UTC")).toInstant());
            } catch (DateTimeParseException ex ) {
                LOGGER.warn("Could not parse given birthday [{}] into a date. Reason: [{}]", birthday, ex.getMessage());
            }
        }
        return Optional.ofNullable(date);
    }

}
