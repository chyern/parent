package com.chyern.parent.connect.processor.connect;

import com.chyern.parent.connect.processor.connect.processor.AbstractConnectProcessor;
import com.chyern.parent.connect.processor.connect.processor.DefaultConnectProcessor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 被注解的接口能自动代理远程调用请求
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

    Class<? extends AbstractConnectProcessor> processor() default DefaultConnectProcessor.class;
}