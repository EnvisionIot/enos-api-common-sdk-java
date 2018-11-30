package com.envisioniot.enos.enosapi.common.exception;

import com.envisioniot.enos.enosapi.common.util.EnOSErrorCode;

/**
 * Created by zuoyong.tang on 2018/8/8.
 */
public class CodeBoundsException extends Exception{
    public CodeBoundsException() {
        super("Code must be larger than " + EnOSErrorCode.MAX_CODE_FOR_PREDEFINED);
    }
}
