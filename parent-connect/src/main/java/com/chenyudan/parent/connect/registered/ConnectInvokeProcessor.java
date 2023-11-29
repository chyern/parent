package com.chenyudan.parent.connect.registered;

import com.chenyudan.parent.connect.Connect;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2022/7/29 15:41
 */
@Component
public class ConnectInvokeProcessor {

    private final ApplicationContext context;

    public ConnectInvokeProcessor(ApplicationContext context) {
        this.context = context;
    }


    public void before(Object proxy, Method method, Object[] args) throws Throwable {
        getAbstractConnectProcessor(method).before(proxy, method, args);
    }

    public Object execute(Object proxy, Method method, Object[] args) throws Throwable {
        return getAbstractConnectProcessor(method).execute(proxy, method, args);
    }

    public void after(Object proxy, Method method, Object[] args, Object result) throws Throwable {
        getAbstractConnectProcessor(method).after(proxy, method, args, result);
    }

    public void throwsException(Object proxy, Method method, Object[] args, Exception exception) {
        getAbstractConnectProcessor(method).throwsException(proxy, method, args, exception);
    }

    private IConnectProcessor getAbstractConnectProcessor(Method method) {
        Connect annotation = method.getDeclaringClass().getAnnotation(Connect.class);
        Class<? extends IConnectProcessor> processor = annotation.processor();
        return context.getBean(processor);
    }

}
