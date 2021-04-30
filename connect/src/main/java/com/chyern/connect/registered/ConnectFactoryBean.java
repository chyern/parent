package com.chyern.connect.registered;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> interfaceType;

    public ConnectFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() {
        ConnectProxy apiProxy = new ConnectProxy();
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

}