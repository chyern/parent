package com.github.chyern.session.config;

import com.github.chyern.session.interceptor.SessionInterceptor;
import com.github.chyern.session.interceptor.WebInterceptor;
import com.github.chyern.session.processor.SessionManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
@Configuration
public class SessionConfig {

    @Bean
    SessionManagement sessionManagement() {
        return new SessionManagement();
    }

    @Bean
    WebInterceptor webInterceptor() {
        return new WebInterceptor();
    }

    @Bean
    SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

}
