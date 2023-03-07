package com.chyern.connect.annotation.method;

import com.chyern.connect.constant.MediaType;
import com.chyern.connect.constant.Method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/20
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * 请求路径
     */
    String value();

    /**
     * 方法类型
     */
    Method method();

    /**
     * 请求类型
     */
    MediaType mediaType() default MediaType.JSON;
}
