package com.github.chyern.session;

import com.github.chyern.session.config.SessionConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Documented
@Import({SessionConfig.class})
@Configuration
public @interface EnableSession {
}
