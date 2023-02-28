package com.chyern.connect.annotation;

import com.chyern.connect.processor.ConnectProcessor;
import com.chyern.connect.processor.IConnectProcessor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Connect {

    /**
     * 请求地址
     * 支持${key},${key:}
     */
    String value();

    Class<? extends IConnectProcessor> processor() default ConnectProcessor.class;
}
