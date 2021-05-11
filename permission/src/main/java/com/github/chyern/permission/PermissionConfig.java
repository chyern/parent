package com.github.chyern.permission;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/11
 */
@Configuration
public class PermissionConfig {

    @Bean
    public PermissionHandlerInterceptor permissionHandlerInterceptor() {
        return new PermissionHandlerInterceptor();
    }
}
