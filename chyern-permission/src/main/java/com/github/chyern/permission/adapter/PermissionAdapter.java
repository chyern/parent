package com.github.chyern.permission.adapter;

import com.github.chyern.permission.interceptor.PermissionInterceptor;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.annotation.Resource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
public class PermissionAdapter extends org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter implements Ordered {

    @Resource
    private PermissionInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 60;
    }
}
