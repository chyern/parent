package com.chenyudan.parent.connect.processor.annotation.resource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 请求参数
 * ?a=xxx&b=xxx
 *
 * @author chenyu
 * @since 2021/4/22
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Queries {
    String value();
}
