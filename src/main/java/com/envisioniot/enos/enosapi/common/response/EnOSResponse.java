package com.envisioniot.enos.enosapi.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


/**
 * Client Response
 */
public class EnOSResponse<T> implements Serializable {
    private static final long serialVersionUID = -2114176208264254258L;

    private int status;

    private String requestId;

    private String msg;

    private String submsg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String body;

    private T data;

    public EnOSResponse() {
        // requestId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == 0 ? true : false;
    }

    public String getSubmsg() {
        return submsg;
    }

    public void setSubmsg(String submsg) {
        this.submsg = submsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
