package com.chenyudan.parent.core.utils;

import lombok.Getter;
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

    @Getter
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static String getPropertyKey(String key) {
        return context.getEnvironment().getProperty(key);
    }

    public static String getPropertyKey(String key, String defaultValue) {
        return context.getEnvironment().getProperty(key, defaultValue);
    }
}
