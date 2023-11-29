package com.chenyudan.parent.connect.registered;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2021/4/24
 */
@Slf4j
public class ConnectFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware {

    private ApplicationContext context;

    private final Class<T> interfaceType;


    public ConnectFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

    @Override
    public T getObject() {
        ConnectInvokeProcessor connectInvokeProcessor = context.getBean(ConnectInvokeProcessor.class);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, new ConnectProxy(connectInvokeProcessor));
    }
}