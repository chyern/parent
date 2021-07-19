package com.github.chyern.session;

import com.github.chyern.session.adapter.SessionAdapter;
import com.github.chyern.session.config.SessionConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;

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
@Import({SessionConfig.class, SessionAdapter.class, SpringHttpSessionConfiguration.class})
@Configuration
public @interface EnableSession {
}
