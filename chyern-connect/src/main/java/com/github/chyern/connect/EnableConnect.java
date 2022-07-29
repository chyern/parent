package com.github.chyern.connect;

import com.github.chyern.connect.processor.ConnectProcessor;
import com.github.chyern.connect.registered.ConnectScanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ConnectScanRegistrar.class, ConnectProcessor.class})
public @interface EnableConnect {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}
