package com.github.chyern.connect.annotations.method;

import java.lang.annotation.*;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GET {
    String value() default "";
}
