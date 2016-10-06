package com.mark.nevexandrunkeeper.exception;

import java.util.logging.Logger;

/**
 * Created by NeVeX on 7/6/2016.
 */
public class APIException extends RuntimeException {

    private static final Logger LOGGER = Logger.getLogger(APIException.class.getName());

    public APIException(String msg, Exception e) {
        super(msg, e);
        LOGGER.severe(msg);
    }

}
