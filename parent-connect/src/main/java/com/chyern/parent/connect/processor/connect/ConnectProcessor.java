package com.chyern.parent.connect.processor.connect;

import com.chyern.parent.connect.processor.IConnectProcessor;
import com.chyern.parent.connect.processor.connect.processor.AbstractConnectProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
public class ConnectProcessor implements IConnectProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void before(Object proxy, Method method, Object[] args) throws Throwable {
        AbstractConnectProcessor connectProcessor = getAbstractConnectProcessor(proxy);
        connectProcessor.before(proxy, method, args);
    }

    @Override
    public Object execute(Object proxy, Method method, Object[] args) throws Throwable {
        AbstractConnectProcessor connectProcessor = getAbstractConnectProcessor(proxy);
        return connectProcessor.execute(proxy, method, args);
    }

    @Override
    public void after(Object proxy, Method method, Object[] args, Object result) throws Throwable {
        AbstractConnectProcessor connectProcessor = getAbstractConnectProcessor(proxy);
        connectProcessor.after(proxy, method, args, result);
    }

    @Override
    public void throwsException(Object proxy, Method method, Object[] args, Exception exception) {
        AbstractConnectProcessor connectProcessor = getAbstractConnectProcessor(proxy);
        connectProcessor.throwsException(proxy, method, args, exception);
    }

    private AbstractConnectProcessor getAbstractConnectProcessor(Object proxy) {
        Connect annotation = proxy.getClass().getAnnotation(Connect.class);
        Class<? extends AbstractConnectProcessor> processor = annotation.processor();
        AbstractConnectProcessor connectProcessor = applicationContext.getBean(processor);
        return connectProcessor;
    }


}
