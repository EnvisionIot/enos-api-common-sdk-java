package com.envisioniot.enos.enosapi.common.resource;

import java.io.InputStream;

/**
 * created by dongdong.wang
 */
public class FileStreamInfo {

    private InputStream content;
    private String originalFileName;
    private String fieldName;
    private String contentType;

    public FileStreamInfo() {
    }

    public FileStreamInfo(InputStream content, String originalFileName, String fieldName, String contentType) {
        this.content = content;
        this.originalFileName = originalFileName;
        this.fieldName = fieldName;
        this.contentType = contentType;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}

