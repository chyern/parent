package com.github.chyern.session;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
@ComponentScan(basePackages = {"com.github.chyern.session"})
@Configuration
public @interface EnableSession {
}
