package com.github.chyern.connect.annotation.method;

import java.lang.annotation.*;

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

    String value();

    Method method();

    MediaType mediaType() default MediaType.JSON;
}
