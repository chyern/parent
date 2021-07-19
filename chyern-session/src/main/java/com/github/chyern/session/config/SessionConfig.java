package com.github.chyern.session.config;

import com.github.chyern.session.interceptor.SessionInterceptor;
import com.github.chyern.session.model.Session;
import com.github.chyern.session.processor.SessionManagement;
import com.github.chyern.session.strategy.SessionRepository;
import com.github.chyern.session.strategy.SessionStrategy;
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
    Session session(){
        return new Session();
    }

    @Bean
    SessionManagement sessionManagement() {
        return new SessionManagement();
    }

    @Bean
    SessionInterceptor sessionInterceptor(){
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

}
