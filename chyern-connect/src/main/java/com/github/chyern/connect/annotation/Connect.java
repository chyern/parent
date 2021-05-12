package com.github.chyern.connect.annotation;

import com.github.chyern.connect.processor.AbstractConnectProcessor;
import com.github.chyern.connect.processor.DefaultConnectProcessor;

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
public @interface Connect {

    String value() default "";

    Class<? extends AbstractConnectProcessor> clazz() default DefaultConnectProcessor.class;
}
