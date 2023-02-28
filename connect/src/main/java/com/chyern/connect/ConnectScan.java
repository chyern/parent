package com.chyern.connect;

import com.chyern.connect.processor.ConnectProcessor;
import com.chyern.connect.registered.ConnectScanRegistrar;
import com.chyern.connect.utils.ConnectUtil;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ConnectScanRegistrar.class, ConnectProcessor.class, ConnectUtil.class})
public @interface ConnectScan {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}
