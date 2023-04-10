package com.chyern.parent.connect.core.registered;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;

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
    private Class<? extends Annotation> annotationClass;

    public ConnectFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public ConnectFactoryBean(Class<T> interfaceType, Class<? extends Annotation> annotationClass) {
        this.interfaceType = interfaceType;
        this.annotationClass = annotationClass;
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
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, new ConnectProxy(null));

        //Collection<IConnectProcessor> connectProcessors = applicationContext.getBeansOfType(IConnectProcessor.class).values();
        //for (IConnectProcessor connectProcessor : connectProcessors) {
        //    Class<? extends Annotation> processorAnnotation = connectProcessor.getProcessorAnnotation();
        //    if (annotationClass.getName().equals(processorAnnotation.getName())) {
        //        log.info("{} is init by {}", interfaceType.getName(), connectProcessors.getClass().getName());
        //        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, new ConnectProxy(connectProcessor));
        //    }
        //}
        //throw new RuntimeException(interfaceType.getName() + "could not find a connectProcessor");

    }
}