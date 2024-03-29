package com.chenyudan.parent.connect.processor.annotation.method;


import com.chenyudan.parent.connect.processor.constant.MediaType;
import com.chenyudan.parent.connect.processor.constant.Method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: TODO
 *
 * @author chenyu
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
