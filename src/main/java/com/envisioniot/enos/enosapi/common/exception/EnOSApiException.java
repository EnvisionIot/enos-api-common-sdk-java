package com.envisioniot.enos.enosapi.common.exception;

/*
 * EEOP Root Exception
 */
public class EnOSApiException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private String errCode;

    private String errMsg;

    public EnOSApiException() {
        super();
    }

    public EnOSApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnOSApiException(String message) {
        super(message);
    }

    public EnOSApiException(Throwable cause) {
        super(cause);
    }

    public EnOSApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

}
