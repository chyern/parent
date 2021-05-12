package com.github.chyern.connect.scan;

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
@Import(ConnectScanRegistrar.class)
public @interface ConnectScan {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}
