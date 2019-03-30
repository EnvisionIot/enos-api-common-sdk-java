package com.envisioniot.enos.enosapi.common.util;

import com.envisioniot.enos.enosapi.common.exception.CodeBoundsException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by zuoyong.tang on 2018/8/8.
 */
public class EnOSErrorCode {
    public static int PREDEFINED = 0;
    public static int USERDEFINED = 1;

    // 0 success
    public static final EnOSErrorCode SUCCESS = new EnOSErrorCode(0, "Success", PREDEFINED);


    public static final EnOSErrorCode INVALID_ARGUMENT = new EnOSErrorCode(400, "Invalid Argument ${param}", PREDEFINED);
    public static final EnOSErrorCode UNAUTHENTICATED = new EnOSErrorCode(401, "Unauthenticated", PREDEFINED);
    public static final EnOSErrorCode PERMISSION_DENIED = new EnOSErrorCode(403, "Permission Denied", PREDEFINED);
    public static final EnOSErrorCode NOT_FOUND = new EnOSErrorCode(404, "Not Found", PREDEFINED);
    public static final EnOSErrorCode METHOD_NOT_ALLOWED = new EnOSErrorCode(405, "Method Not Allowed", PREDEFINED);
    public static final EnOSErrorCode ALREADY_EXISTS = new EnOSErrorCode(409, "Already Exists", PREDEFINED);
    public static final EnOSErrorCode REQUEST_BODY_TOO_LARGE = new EnOSErrorCode(413, "Request Body Too Large", PREDEFINED);
    public static final EnOSErrorCode PARAMETER_OUT_OF_RANGE = new EnOSErrorCode(415, "Parameter Out Of Range", PREDEFINED);
    public static final EnOSErrorCode RESOURCE_EXHAUSTED = new EnOSErrorCode(429, "Resource Exhausted", PREDEFINED);
    public static final EnOSErrorCode RESULT_SIZE_TOO_LARGE = new EnOSErrorCode(430, "Result size too large", PREDEFINED);

    public static final EnOSErrorCode APP_TIMESTAMP_FAIL = new EnOSErrorCode(497, "App Timestamp Checked Fail", PREDEFINED);
    public static final EnOSErrorCode APP_SIGN_FAIL = new EnOSErrorCode(497, "App Signature Checked Fail", PREDEFINED);
    public static final EnOSErrorCode APP_RESOURCE_FAIL = new EnOSErrorCode(498, "App Resource Permission Denied", PREDEFINED);
    public static final EnOSErrorCode APP_API_FAIL = new EnOSErrorCode(498, "App Api Permission Denied", PREDEFINED);
    public static final EnOSErrorCode CLIENT_ERROR = new EnOSErrorCode(499, "Client Error", PREDEFINED);

    public static final EnOSErrorCode INTERNAL_SERVER_ERROR = new EnOSErrorCode(500, "Internal Server Error", PREDEFINED);
    public static final EnOSErrorCode NOT_IMPLEMENTED = new EnOSErrorCode(501, "Not Implemented", PREDEFINED);
    public static final EnOSErrorCode SERVICE_UNAVAILABLE = new EnOSErrorCode(503, "Service Unavailable", PREDEFINED);
    public static final EnOSErrorCode SERVICE_TIMEOUT = new EnOSErrorCode(504, "Service Timeout", PREDEFINED);

    //iam error code
    public static final EnOSErrorCode UNSUPPORTED_OPERATION = new EnOSErrorCode(601, "Unsupported Operation for Particular Resource", PREDEFINED);

    // 0 - 599 common code
    public static int MAX_CODE_FOR_PREDEFINED = 599;

    //data service code(data app)
    /**700~ */
    public static final EnOSErrorCode DATA_SERVICE_ERROR = new EnOSErrorCode(701, "data service ERROR.", USERDEFINED);
    public static final EnOSErrorCode DATA_SERVICE_EXEC_ERROR = new EnOSErrorCode(702, "An error occurred when execute query in data service.", USERDEFINED);

    private int code;
    private String msg;
    private int type;

    private EnOSErrorCode(int code, String msg, int type) {
        this.code = code;
        this.msg = msg;
        this.type = type;
    }

    public EnOSErrorCode(int code, String msg) throws CodeBoundsException {
        this(code, msg, USERDEFINED);
        if (code <= MAX_CODE_FOR_PREDEFINED) {
            throw new CodeBoundsException();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) throws CodeBoundsException {
        if (code <= MAX_CODE_FOR_PREDEFINED) {
            throw new CodeBoundsException();
        }
        this.code = code;
    }

    /**
     * try to return message replaced placeholder by reps
     * @param reps
     * @return
     */
    public String getMsg(String ...reps) {
        String msg = this.msg;
        if (msg == null) {
            msg = "";
        }
        for (String rep : reps) {
            msg = msg.replaceAll("\\$\\{[^\\}]*\\}", rep);
        }
        // delete ${} from msg
        return msg.replaceAll("\\$\\{([^\\}]*)\\}", "$1");
    }

    public void setMsg(String msg, String ...reps) {
        if (msg == null || msg.equals("")) {
            msg = this.msg;
        }
        for (String rep : reps) {
            msg = msg.replaceAll("\\$\\{[^\\}]*\\}", rep);
        }
        this.msg = msg;
    }

    public EnOSErrorCode replaceMsg(String msg, String ...reps) {
        EnOSErrorCode newCode = new EnOSErrorCode(this.code, this.msg, this.type);

        if (msg == null || msg.equals("")) {
            msg = newCode.msg;
        }
        for (String rep : reps) {
            msg = msg.replaceAll("\\$\\{[^\\}]*\\}", rep);
        }
        newCode.msg = msg;
        return newCode;
    }
}
