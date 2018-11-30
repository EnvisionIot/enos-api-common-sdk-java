package com.envisioniot.enos.enosapi.common.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnOSPathVariable {

    String name() default ""; //PathVariable的value/name值
}
