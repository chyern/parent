package com.github.chyern.connect.registered;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware {

    private ApplicationContext context;

    private final Class<T> interfaceType;

    public ConnectFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() {
        ConnectProxy apiProxy = new ConnectProxy(context);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, apiProxy);
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}