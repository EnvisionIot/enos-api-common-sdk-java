package com.envisioniot.enos.enosapi.common.annotation;

import com.envisioniot.enos.enosapi.common.enumeration.TransferType;

import java.lang.annotation.*;

/**
 * created by dongdong.wang
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnOSTransferFileField {
    String name() default ""; //后端接收的value/name值
    TransferType type() default TransferType.UPLOAD;
}
