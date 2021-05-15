package com.github.chyen.session.config;

import com.github.chyen.session.adapter.SessionAdapter;
import com.github.chyen.session.interceptor.SessionInterceptor;
import com.github.chyen.session.model.Session;
import com.github.chyen.session.processor.SessionManagement;
import com.github.chyen.session.strategy.SessionRepository;
import com.github.chyen.session.strategy.SessionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
@Configuration
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
    SessionAdapter sessionAdapter(){
        return new SessionAdapter();
    }

    @Bean
    Session session() {
        return new Session();
    }

    @Bean
    SessionRepository sessionRepository() {
        return new SessionRepository();
    }

    @Bean
    SessionManagement sessionManagement() {
        return new SessionManagement();
    }
}
