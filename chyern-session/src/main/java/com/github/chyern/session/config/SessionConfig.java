package com.github.chyern.session.config;

import com.github.chyern.session.strategy.SessionRepository;
import com.github.chyern.session.strategy.SessionStrategy;
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
    HttpSessionStrategy httpSessionStrategy() {
        return new SessionStrategy();
    }

    @Bean
    SessionRepository sessionRepository() {
        return new SessionRepository();
    }
}
