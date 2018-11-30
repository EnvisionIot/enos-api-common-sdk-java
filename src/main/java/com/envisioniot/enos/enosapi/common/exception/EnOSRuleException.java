package com.envisioniot.enos.enosapi.common.exception;

/*
 * Exception caused by violation of the rules
 */
public class EnOSRuleException extends EnOSApiException {

    private static final long serialVersionUID = -7787145910600194272L;

    public EnOSRuleException(String errCode, String errMsg) {
        super(errCode, errMsg);
    }

    public EnOSRuleException(Throwable cause) {
        super(cause);
    }
}
