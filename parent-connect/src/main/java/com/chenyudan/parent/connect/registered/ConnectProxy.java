package com.chenyudan.parent.connect.registered;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectProxy implements InvocationHandler {

    private final ConnectInvokeProcessor connectInvokeProcessor;

    public ConnectProxy(ConnectInvokeProcessor connectInvokeProcessor) {
        this.connectInvokeProcessor = connectInvokeProcessor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            connectInvokeProcessor.before(proxy, method, args);
            Object execute = connectInvokeProcessor.execute(proxy, method, args);
            connectInvokeProcessor.after(proxy, method, args, execute);
            return execute;
        } catch (Exception exception) {
            connectInvokeProcessor.throwsException(proxy, method, args, exception);
            throw exception;
        }
    }

}
