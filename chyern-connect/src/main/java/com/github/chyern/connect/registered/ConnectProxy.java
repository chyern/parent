package com.github.chyern.connect.registered;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.exception.Exception;
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
        try {
            connectProcessor.before(proxy, method, args);
            Object execute = connectProcessor.execute(proxy, method, args);
            connectProcessor.after(proxy, method, args, execute);
            return execute;
        } catch (Exception exception) {
            connectProcessor.throwsException(proxy, method, args, exception);
            throw new Exception(ErrorEnum.CONNECT_ERROR);
        }
    }

}
