package com.mark.nevexandrunkeeper.runkeeper.api.exception;

/**
 * Created by NeVeX on 7/11/2016.
 */
public final class RunKeeperAPIException extends Exception {

    public RunKeeperAPIException(String msg) {
        super(msg);
    }

    public RunKeeperAPIException(String msg, Exception ex) {
        super(msg, ex);
    }

}
