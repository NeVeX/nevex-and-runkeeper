package com.mark.nevexandrunkeeper.exception;

import java.util.logging.Logger;

/**
 * Created by NeVeX on 7/11/2016.
 */
public class RunKeeperException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(RunKeeperException.class.getName());

    public RunKeeperException(String msg) {
        super(msg);
        LOGGER.warning(msg);
    }

}
