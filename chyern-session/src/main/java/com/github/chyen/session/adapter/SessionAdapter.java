package com.github.chyen.session.adapter;

import com.github.chyen.session.interceptor.SessionInterceptor;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.annotation.Resource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
public class SessionAdapter extends org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter implements Ordered {

    @Resource
    private SessionInterceptor interceptor;

    @Resource
    private SessionExcludePathPatterns excludePathPatterns;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns.excludePathPatterns());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }
}
