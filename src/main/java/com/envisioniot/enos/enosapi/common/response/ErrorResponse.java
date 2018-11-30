/**
 * Project: eeop-service
 * <p>
 * Copyright http://www.envisioncn.com/
 * All rights reserved.
 *
 * @author xiaomin.zhou
 */
package com.envisioniot.enos.enosapi.common.response;

import com.envisioniot.enos.enosapi.common.util.EnOSErrorCode;

/**
 * Error Response Define
 */
public class ErrorResponse extends EnOSResponse {

    private static final long serialVersionUID = 6464638679820005657L;

    public ErrorResponse(EnOSErrorCode error) {
        setError(error);
    }

    public ErrorResponse(EnOSErrorCode error, String msg) {
        setErrorCode(error.getCode());
        setErrorMsg(msg);
    }

    public ErrorResponse(EnOSErrorCode error, Throwable e) {
        setErrorCode(error.getCode());
        setErrorMsg(e.getMessage());
    }

    public void setError(EnOSErrorCode error) {
        setErrorCode(error.getCode());
        setErrorMsg(error.getMsg());
    }

    public void setErrorCode(int code) {
        super.setStatus(code);
    }

    public void setErrorMsg(String msg) {
        super.setMsg(msg);
    }

    public void setSubErrorMsg(String subMsg) {
        super.setSubmsg(subMsg);
    }
}
