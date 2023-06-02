package com.chenyudan.parent.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/23
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static <T> T getBean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    public static String getPropertyKey(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    public static String getPropertyKey(String key, String defaultValue) {
        return applicationContext.getEnvironment().getProperty(key, defaultValue);
    }
}
