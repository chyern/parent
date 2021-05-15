package com.github.chyern.permission.annotation;

import java.lang.annotation.*;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    String permissionCode() default "";
}
