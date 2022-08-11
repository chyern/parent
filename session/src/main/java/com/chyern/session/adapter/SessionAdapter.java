package com.chyern.session.adapter;

import com.chyern.session.config.EnvironmentConfig;
import com.chyern.session.interceptor.SessionInterceptor;
import com.chyern.session.interceptor.WebInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
@Configuration
public class SessionAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    private WebInterceptor webInterceptor;

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Autowired
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
