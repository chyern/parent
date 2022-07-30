package com.github.chyern.connect.annotation;

import com.github.chyern.connect.processor.ConnectProcessor;
import com.github.chyern.connect.processor.IConnectProcessor;

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

    String value();

    Class<? extends IConnectProcessor> processor() default ConnectProcessor.class;
}
