package com.github.chyen.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
@Configuration
@EnableSpringHttpSession
public class SessionConfig {

    @Bean
    public SessionHandlerInterceptor sessionHandlerInterceptor() {
        return new SessionHandlerInterceptor();
    }

    @Bean
    HttpSessionStrategy httpSessionStrategy() {
        return new SessionStrategy();
    }

    @Bean
    SessionRepository sessionRepository() {
        return new SessionRepository();
    }
}
