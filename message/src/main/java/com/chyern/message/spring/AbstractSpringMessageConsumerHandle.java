package com.chyern.message.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/10 12:22
 */
public abstract class AbstractSpringMessageConsumerHandle<T> implements ApplicationContextAware, InitializingBean, ISpringMessageConsumer<T> {

    private ApplicationContext context = null;
    private static Map<Class, AbstractSpringMessageConsumerHandle> handleMap = new HashMap<>();

    public static AbstractSpringMessageConsumerHandle getHandleMap(Class tClass) {
        return handleMap.get(tClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        handleMap.put(getActualType(), this);
    }

    public void product(T t) {
        context.publishEvent(t);
    }

    @EventListener
    public void consume(T t) {
        this.handle(t);
    }

    private Class<T> getActualType() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }
}