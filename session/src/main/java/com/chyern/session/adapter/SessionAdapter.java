package com.chyern.session.adapter;

import com.chyern.session.config.EnvironmentConfig;
import com.chyern.session.interceptor.SessionInterceptor;
import com.chyern.session.interceptor.WebInterceptor;
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
    private SessionInterceptor sessionInterceptor;

    @Resource
    private EnvironmentConfig environmentConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(environmentConfig.getExcludePathPatterns());
    }

}
