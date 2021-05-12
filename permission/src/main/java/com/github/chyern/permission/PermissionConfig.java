package com.github.chyern.permission;

import org.springframework.context.annotation.Bean;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/11
 */
public class PermissionConfig {

    @Bean
    public PermissionHandlerInterceptor permissionHandlerInterceptor() {
        return new PermissionHandlerInterceptor();
    }
}
