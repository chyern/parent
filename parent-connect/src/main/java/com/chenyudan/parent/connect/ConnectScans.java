package com.chenyudan.parent.connect;

import com.chenyudan.parent.connect.registered.ConnectScanRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 扫描类
 *
 * @author Chyern
 * @since 2021/4/22
 */
@Configuration
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ConnectScanRegistrar.class})
public @interface ConnectScans {

    ConnectScan[] value() default {};
}
