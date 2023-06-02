package com.chenyudan.parent.session.adapter;

import com.chenyudan.parent.session.config.SessionConfigProperties;
import com.chenyudan.parent.session.interceptor.SessionInterceptor;
import com.chenyudan.parent.session.interceptor.WebInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
@Component
public class SessionAdapter extends WebMvcConfigurerAdapter {

    @Resource
    private WebInterceptor webInterceptor;

    @Resource
    private SessionInterceptor sessionInterceptor;

    @Resource
    private SessionConfigProperties sessionConfigProperties;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(sessionConfigProperties.getExcludePathPatterns());
    }

}
