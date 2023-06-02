package com.chenyudan.parent.connect.registered;

import com.chenyudan.parent.connect.processor.IConnectProcessor;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectProxy implements InvocationHandler {
    private final IConnectProcessor connectProcessor;

    public ConnectProxy(IConnectProcessor connectProcessor) {
        this.connectProcessor = connectProcessor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            connectProcessor.before(proxy, method, args);
            Object execute = connectProcessor.execute(proxy, method, args);
            connectProcessor.after(proxy, method, args, execute);
            return execute;
        } catch (Exception exception) {
            connectProcessor.throwsException(proxy, method, args, exception);
            throw exception;
        }
    }

}
