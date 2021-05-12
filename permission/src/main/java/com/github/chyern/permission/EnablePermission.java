package com.github.chyern.permission;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/12
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({PermissionConfig.class, PermissionConfigurerAdapter.class})
public @interface EnablePermission {
}