package com.envisioniot.enos.enosapi.common.request;

import com.envisioniot.enos.enosapi.common.annotation.EnOSPathVariable;
import com.envisioniot.enos.enosapi.common.annotation.EnOSRequestBody;
import com.envisioniot.enos.enosapi.common.annotation.EnOSTransferFileField;
import com.envisioniot.enos.enosapi.common.enumeration.TransferType;
import com.envisioniot.enos.enosapi.common.exception.EnOSClientException;
import com.envisioniot.enos.enosapi.common.exception.EnOSRuleException;
import com.envisioniot.enos.enosapi.common.resource.EnOSTransferFileInfo;
import com.envisioniot.enos.enosapi.common.response.EnOSResponse;
import com.envisioniot.enos.enosapi.common.util.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EnOS_API_V2的request请求基类
 */
public abstract class EnOSRequest<T extends EnOSResponse> {

    public abstract String getRequestMethod();

    public abstract String getApiMethodName();

    /**
     * 获取Request中放在url中的参数, 即不被注解EnOSPathVariable和EnOSRequestBody修饰的参数
     *
     * @return java.util.Map<java.lang.String               ,               java.lang.String>
     **/
    public Map<String, String> getUrlParams() {
        EnOSHashMap urlParams = new EnOSHashMap();
        Field[] fields = this.getClass().getDeclaredFields();//不包含父类字段
        if (fields != null) {
            for (Field field : fields) {
                field.setAccessible(true);//防止私有变量访问异常
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;// 不传输静态变量
                }
                try {
                    String fieldName = field.getName();
                    Object fieldValue = field.get(this);
                    Class fieldType = field.getType();
                    String fieldTypeName = fieldType.getSimpleName();
                    boolean isPathVariable = field.getAnnotation(EnOSPathVariable.class) != null;
                    boolean isRequestBody = field.getAnnotation(EnOSRequestBody.class) != null;
                    boolean isFileTransferField = field.getAnnotation(EnOSTransferFileField.class) != null;
                    if ("API_METHOD".equals(fieldName)) {
                        continue;//不传输API_METHOD字段
                    }
                    if (fieldValue == null || "".equals("" + fieldValue)) {
                        continue;
                    }
                    if (isPathVariable || isRequestBody || isFileTransferField) {
                        continue;
                    }
                    urlParams.put(fieldName, fieldValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return urlParams;
    }

    /**
     * @return java.util.Map<java.lang.String                               ,                                                               java.lang.String>
     * @Description 获取Request中放在url中的PathVariable（@EnOSPathVariable标注的变量）
     **/
    public Map<String, String> getPathParams() throws EnOSClientException {
        EnOSHashMap pathParams = new EnOSHashMap();
        Field[] fields = this.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                field.setAccessible(true);//防止私有变量访问异常
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;// 不传输静态变量
                }
                try {
                    String fieldName = field.getName();
                    Object fieldValue = field.get(this);

                    EnOSPathVariable pathVariableAnno = field.getAnnotation(EnOSPathVariable.class);

                    if (pathVariableAnno != null) {
                        if (fieldValue == null || StringUtils.isEmpty(fieldValue.toString())) {
                            throw new EnOSClientException(EnOSErrorCode.INVALID_ARGUMENT.replaceMsg(null, fieldName), fieldName + " is required");
                        }
                        if (!fieldValue.toString().replaceAll("\\s", "").equals(fieldValue.toString())) {
                            throw new EnOSClientException(EnOSErrorCode.INVALID_ARGUMENT.replaceMsg(null, fieldName), fieldName + " cannot contains space");
                        }
                        String pathParamName = StringUtils.isEmpty(pathVariableAnno.name()) ? fieldName : pathVariableAnno.name();
                        pathParams.put(pathParamName, fieldValue);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return pathParams;
    }

    /**
     * 获取Request中放在JsonBody中的参数
     *
     * @return java.lang.String
     * @throws EnOSClientException 当对象参数超过一个时，抛出此异常
     **/
    public String getJsonParam() throws EnOSClientException {
        int jsonCount = 0;
        String jsonParam = null;
        Field[] fields = this.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                field.setAccessible(true);//防止私有变量访问异常
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;// 不传输静态变量
                }
                try {
                    boolean isRequestBody = field.getAnnotation(EnOSRequestBody.class) != null;
                    if (isRequestBody) {
                        String fieldName = field.getName();
                        Object fieldValue = field.get(this);
                        Class fieldType = field.getType();
                        String fieldTypeName = fieldType.getSimpleName();

                        jsonCount++;
                        if (fieldValue == null) {
                            jsonParam = null;
                        } else {
                            jsonParam = JsonParser.toJson(fieldValue);
                        }
                        if (jsonCount >= 2) {
                            throw new EnOSClientException(EnOSErrorCode.INVALID_ARGUMENT, "only support one json body");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonParam;
    }

    /**
     * 获取Request请求的API的URl（替换PathVariable，不包含QueryStringParams）
     *
     * @return java.lang.String 替换了替换PathVariable的Request请求的URL
     * @throws EnOSClientException
     */
    public String getApiPath() throws EnOSClientException {
        String apiPath = getApiMethodName();
        Map<String, String> pathParams = getPathParams();

        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            try {
                apiPath = apiPath.replace("{" + entry.getKey() + "}", entry.getValue());
            } catch (Exception e) {
                throw new EnOSClientException(EnOSErrorCode.CLIENT_ERROR, "Failed to generate API Path");
            }
        }
        return apiPath;
    }

    public List<EnOSTransferFileInfo> getTransferFiles() throws EnOSClientException {
        List<EnOSTransferFileInfo> transferFileInfos = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                field.setAccessible(true);//防止私有变量访问异常
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;// 不传输静态变量
                }
                boolean isTransferFile = field.getAnnotation(EnOSTransferFileField.class) != null;
                if (isTransferFile) {
                    EnOSTransferFileField anno = field.getAnnotation(EnOSTransferFileField.class);
                    String transferName = anno.name();
                    TransferType transferType = anno.type();
                    String fieldName = field.getName();
                    if (StringUtils.isEmpty(transferName)) {
                        transferName = fieldName;
                    }
                    Object fieldValue = null;
                    try {
                        fieldValue = field.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    Class fieldType = field.getType();
                    //处理的field类型包括：File、File[]、Map<String, File[]>
                    if (fieldType.equals(File.class)) {
                        EnOSTransferFileInfo fileInfo = new EnOSTransferFileInfo(transferName, transferType, (File) fieldValue);
                        transferFileInfos.add(fileInfo);
                    } else if (fieldType.equals(File[].class)) {
                        File[] filesValue = (File[]) fieldValue;
                        if (filesValue != null) {
                            for (File fileValue : filesValue) {
                                EnOSTransferFileInfo fileInfo = new EnOSTransferFileInfo(transferName, transferType, fileValue);
                                transferFileInfos.add(fileInfo);
                            }
                        }
                    } else if (fieldType.equals(Map.class)) {
                        Type geneType = field.getGenericType();
                        Type keyType = TypeReflectUtils.getGenricParameter(geneType, 0);
                        Type valueType = TypeReflectUtils.getGenricParameter(geneType, 1);
                        if (keyType.equals(String.class)) {
                            if (valueType.equals(File.class)) {
                                Map<String, File> fileMap = (HashMap<String, File>) fieldValue;
                                if (fileMap != null) {
                                    for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                                        EnOSTransferFileInfo fileInfo = new EnOSTransferFileInfo(entry.getKey(), transferType, entry.getValue());
                                        transferFileInfos.add(fileInfo);
                                    }
                                }
                            }
                            if (valueType.equals(File[].class)) {
                                Map<String, File[]> filesMap = (HashMap<String, File[]>) fieldValue;
                                if (filesMap != null) {
                                    for (Map.Entry<String, File[]> entry : filesMap.entrySet()) {
                                        if (entry.getValue() != null) {
                                            for (File file : entry.getValue()) {
                                                EnOSTransferFileInfo fileInfo = new EnOSTransferFileInfo(entry.getKey(), transferType, file);
                                                transferFileInfos.add(fileInfo);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        throw new EnOSClientException(EnOSErrorCode.INVALID_ARGUMENT, "transfered filed type must be java.io.File");
                    }
                }
            }
        }
        return transferFileInfos;
    }

    public boolean hasFileUpload() {
        List<EnOSTransferFileInfo> transferFileInfos = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                field.setAccessible(true);//防止私有变量访问异常
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;// 不传输静态变量
                }
                EnOSTransferFileField transferFileFieldAnno = field.getAnnotation(EnOSTransferFileField.class);
                if(transferFileFieldAnno != null) {
                    if(transferFileFieldAnno.type().equals(TransferType.UPLOAD)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前Request实例的泛型类型（T extends EnOSResponse）
     *
     * @return java.lang.reflect.Type 当前实例的泛型类型
     **/
    public Type getResponseType() {
        return TypeReflectUtils.getActualGenricType(this.getClass(), 0);
    }

    public abstract void check() throws EnOSRuleException;

}