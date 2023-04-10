package com.chyern.parent.connect.core;

import com.chyern.parent.connect.annotation.Connect;
import com.chyern.parent.connect.core.config.ConnectConfig;
import com.chyern.parent.connect.core.registered.ConnectScanRegistrar;
import com.chyern.parent.connect.processor.ConnectProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Annotation;
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
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ConnectProcessor.class,
        ConnectConfig.class,
        ConnectScanRegistrar.class})
public @interface ConnectScan {

    /**
     * 扫描路径
     */
    String[] basePackages() default {};

    /**
     * 扫描注解
     */
    Class<? extends Annotation>[] annotations() default {Connect.class};
}
