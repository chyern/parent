package com.chyern.connect.scan;

import com.chyern.connect.handler.AbstractConnectHandler;
import com.chyern.connect.handler.DefaultConnectHandler;

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

    Class<? extends AbstractConnectHandler> clazz() default DefaultConnectHandler.class;
}
