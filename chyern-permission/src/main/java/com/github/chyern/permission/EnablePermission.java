package com.github.chyern.permission;

import com.github.chyern.permission.adapter.PermissionAdapter;
import com.github.chyern.permission.config.PermissionConfig;
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
@Import({PermissionConfig.class, PermissionAdapter.class})
public @interface EnablePermission {
}