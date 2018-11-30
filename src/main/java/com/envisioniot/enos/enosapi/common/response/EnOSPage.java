package com.envisioniot.enos.enosapi.common.response;

import com.envisioniot.enos.enosapi.common.util.JsonParser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: EnOSResponse分页信息
 */
public class EnOSPage<T> implements Serializable {

    private static final long serialVersionUID = -5537308055898546422L;

    private Object pageToken; // 当前分页号

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageSize; // 每页最多显示数据条数

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalSize; // 总数据条数

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object nextPageToken;//下一页的分页号

    private List<T> data; //  当前页数据条数

    public EnOSPage() {

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Object getPageToken() {
        return pageToken;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Object getNextPageToken() {
        return nextPageToken;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setNextPageToken(Object nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public <L> L getPageToken(Class<L> tokenType) {
        return (L)pageToken;
    }

    public <L> L getNextPageToken(Class<L> tokenType) {
        return (L) nextPageToken;
    }

    public void setPageToken(Object pageToken) {
        this.pageToken = pageToken;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getTotalSize() {
        return totalSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @JsonIgnore
    public int getTotalPage() {
        return (int)Math.ceil((float)totalSize / pageSize);
    }
}
