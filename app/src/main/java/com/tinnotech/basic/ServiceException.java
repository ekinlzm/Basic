package com.tinnotech.basic;

/**
 * Created by LZM on 2017/2/9.
 */

public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;
    private int status;

    public ServiceException(int status, String message) {
        this(status, message, null);
    }

    public ServiceException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "status:" + this.status + ",errmsg:" + this.getMessage();
    }

}
