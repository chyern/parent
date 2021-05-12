package com.github.chyen.session;

import com.github.chyen.session.adapter.SessionAdapter;
import com.github.chyen.session.config.SessionConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/12
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({SessionConfig.class, SessionAdapter.class})
public @interface EnableSession {
}
