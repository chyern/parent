package com.github.chyen.session.config;

import com.github.chyen.session.interceptor.SessionInterceptor;
import com.github.chyen.session.processor.SessionManagement;
import com.github.chyen.session.strategy.SessionRepository;
import com.github.chyen.session.strategy.SessionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
@EnableSpringHttpSession
public class SessionConfig {

    @Bean
    public SessionInterceptor sessionHandlerInterceptor() {
        return new SessionInterceptor();
    }

    @Bean
    HttpSessionStrategy httpSessionStrategy() {
        return new SessionStrategy();
    }

    @Bean
    SessionRepository sessionRepository() {
        return new SessionRepository();
    }

    @Bean
    SessionManagement sessionManagement(){
        return new SessionManagement();
    }
}
