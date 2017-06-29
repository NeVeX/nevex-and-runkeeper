package com.mark.nevexandrunkeeper.runkeeper.exception;

/**
 * Created by Mark Cunningham on 6/27/2017.
 */
public final class RunKeeperException extends Exception {

    public RunKeeperException(String msg) {
        super(msg);
    }

    public RunKeeperException(Exception ex) {
        super(ex);
    }

    public RunKeeperException(String msg, Exception ex) {
        super(msg, ex);
    }

}
