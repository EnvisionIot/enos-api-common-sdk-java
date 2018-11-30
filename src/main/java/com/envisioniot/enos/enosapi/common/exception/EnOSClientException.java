package com.envisioniot.enos.enosapi.common.exception;

import com.envisioniot.enos.enosapi.common.util.EnOSErrorCode;

/**
 * @Description: EnOS API的Client端异常
 */
public class EnOSClientException extends Exception {

    private EnOSErrorCode enosStatus = EnOSErrorCode.CLIENT_ERROR;
    private String errMsg;

    public EnOSClientException() {
        super();
    }

    public EnOSClientException(String message) {
        super(message);
        this.errMsg = message;
    }

    public EnOSClientException(EnOSErrorCode enosStatus) {
        super(enosStatus.getMsg());
        this.enosStatus = enosStatus;
        this.errMsg = enosStatus.getMsg();
    }

    public EnOSClientException(EnOSErrorCode enosStatus, String message) {
        super(message);
        this.enosStatus = enosStatus;
        this.errMsg = message;
    }

    public EnOSClientException(Throwable cause) {
        super(cause);
    }

    public EnOSClientException(String message, Throwable cause) {
        super(cause);
        this.errMsg = message;
    }

    public EnOSErrorCode getEnosStatus() {
        return enosStatus;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
