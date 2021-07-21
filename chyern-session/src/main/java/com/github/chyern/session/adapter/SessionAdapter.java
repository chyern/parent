package com.github.chyern.session.adapter;

import com.github.chyern.session.interceptor.SessionInterceptor;
import com.github.chyern.session.interceptor.WebInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
@Configuration
public class SessionAdapter extends WebMvcConfigurerAdapter {

    @Resource
    private WebInterceptor webInterceptor;

    @Resource
    private SessionInterceptor interceptor;

    @Value("${chyern.session.exclude.path.patterns:}")
    private String excludePathPatterns;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns.split(";"));
    }

}
