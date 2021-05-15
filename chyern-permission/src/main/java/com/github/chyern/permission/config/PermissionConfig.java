package com.github.chyern.permission.config;

import com.github.chyern.permission.adapter.PermissionAdapter;
import com.github.chyern.permission.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/11
 */
public class PermissionConfig {

    @Bean
    public PermissionInterceptor permissionHandlerInterceptor() {
        return new PermissionInterceptor();
    }

    @Bean
    public PermissionAdapter permissionAdapter(){
        return new PermissionAdapter();
    }
}
