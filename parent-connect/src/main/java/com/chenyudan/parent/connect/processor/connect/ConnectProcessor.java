package com.chenyudan.parent.connect.processor.connect;

import com.chenyudan.parent.connect.processor.IConnectProcessor;
import com.chenyudan.parent.connect.processor.connect.processor.AbstractConnectProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
@Component
public class ConnectProcessor implements IConnectProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void before(Object proxy, Method method, Object[] args) throws Throwable {
        AbstractConnectProcessor connectProcessor = getAbstractConnectProcessor(method);
        connectProcessor.before(proxy, method, args);
    }

    @Override
    public Object execute(Object proxy, Method method, Object[] args) throws Throwable {
        AbstractConnectProcessor connectProcessor = getAbstractConnectProcessor(method);
        return connectProcessor.execute(proxy, method, args);
    }

    @Override
    public void after(Object proxy, Method method, Object[] args, Object result) throws Throwable {
        AbstractConnectProcessor connectProcessor = getAbstractConnectProcessor(method);
        connectProcessor.after(proxy, method, args, result);
    }

    @Override
    public void throwsException(Object proxy, Method method, Object[] args, Exception exception) {
        AbstractConnectProcessor connectProcessor = getAbstractConnectProcessor(method);
        connectProcessor.throwsException(proxy, method, args, exception);
    }

    private AbstractConnectProcessor getAbstractConnectProcessor(Method method) {
        Connect annotation = method.getDeclaringClass().getAnnotation(Connect.class);
        Class<? extends AbstractConnectProcessor> processor = annotation.processor();
        AbstractConnectProcessor connectProcessor = applicationContext.getBean(processor);
        return connectProcessor;
    }


}
