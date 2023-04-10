package com.chyern.parent.connect.registered;

import com.chyern.parent.connect.processor.IConnectProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
@Slf4j
public class ConnectFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final Class<T> interfaceType;
    private final Class<? extends IConnectProcessor> processor;


    public ConnectFactoryBean(Class<? extends IConnectProcessor> processor, Class<T> interfaceType) {
        this.interfaceType = interfaceType;
        this.processor = processor;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
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
    public T getObject() {
        IConnectProcessor connectProcessor = applicationContext.getBean(processor);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, new ConnectProxy(connectProcessor));
    }
}