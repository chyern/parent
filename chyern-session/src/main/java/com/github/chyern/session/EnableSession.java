package com.github.chyern.session;

import com.github.chyern.session.config.SessionScanRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(value = SessionScanRegistrar.class)
@Configuration
public @interface EnableSession {
}
