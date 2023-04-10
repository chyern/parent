package com.chyern.parent.connect.core.registered;

import com.chyern.parent.connect.processor.ConnectProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAttributes;

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

    private final AnnotationAttributes annotationAttributes;

    public ConnectFactoryBean(Class<T> interfaceType, AnnotationAttributes annotationAttributes) {
        this.interfaceType = interfaceType;
        this.annotationAttributes = annotationAttributes;
    }

    @Override
    public T getObject() {
        ConnectProcessor connectProcessor = applicationContext.getBean(ConnectProcessor.class);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, new ConnectProxy(connectProcessor));
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
        this.applicationContext = applicationContext;
    }
}