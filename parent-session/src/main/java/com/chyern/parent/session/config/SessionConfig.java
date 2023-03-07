package com.chyern.parent.session.config;

import com.chyern.parent.session.registrar.SessionScanRegistrar;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/3/6 10:57
 */
@Configuration
@EnableConfigurationProperties({SessionConfigProperties.class})
public class SessionConfig {

    @Bean
    public SessionScanRegistrar sessionScanRegistrar() {
        return new SessionScanRegistrar();
    }

}
