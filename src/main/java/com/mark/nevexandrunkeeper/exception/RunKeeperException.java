package com.mark.nevexandrunkeeper.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by NeVeX on 7/11/2016.
 */
public final class RunKeeperException extends Exception {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunKeeperException.class.getName());

    public RunKeeperException(String msg) {
        super(msg);
        LOGGER.error(msg);
    }

}
