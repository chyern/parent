package com.github.chyern.connect.registered;

import com.github.chyern.connect.annotation.Connect;
import com.github.chyern.connect.processor.IConnectProcessor;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectProxy implements InvocationHandler {

    private ApplicationContext context;

    public ConnectProxy(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connect connect = method.getDeclaringClass().getAnnotation(Connect.class);
        IConnectProcessor connectProcessor = context.getBean(connect.clazz());
        return connectProcessor.execute(proxy, method, args);
    }

}
