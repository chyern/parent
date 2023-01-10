package com.chyern.message.spring;

import com.chyern.message.spring.domain.SpringMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/10 12:22
 */
public abstract class AbstractSpringMessageConsumerHandle<T extends SpringMessage> implements ApplicationContextAware, InitializingBean {

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
        Boolean sync = t.getSync();
        Boolean afterCommit = t.getAfterCommit();
        Boolean translation = t.getTranslation();
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) context.getBean("springMessageThreadPool");

        if (sync) {
            this.handle(t);
        } else {
            threadPoolExecutor.execute(() -> this.handle(t));
        }
    }

    protected abstract void handle(T t);

    private Class<T> getActualType() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }
}
