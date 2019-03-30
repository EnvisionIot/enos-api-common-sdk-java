package com.envisioniot.enos.enosapi.common.annotation;

import java.lang.annotation.*;

/**
 * @author dongdong.wang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnOSDownloadOperation {
}
