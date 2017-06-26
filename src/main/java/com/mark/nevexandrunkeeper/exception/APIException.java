package com.mark.nevexandrunkeeper.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by NeVeX on 7/6/2016.
 */
public final class APIException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunKeeperException.class.getName());

    public APIException(String msg, Exception e) {
        super(msg, e);
        LOGGER.error(msg);
    }

}
