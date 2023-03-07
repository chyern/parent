package com.chyern.parent.connect.registered;

import com.chyern.parent.connect.annotation.Connect;
import com.chyern.parent.connect.processor.IConnectProcessor;
import com.chyern.parent.spi.exception.BaseException;
import com.chyern.parent.spi.exception.enums.ConnectErrorEnum;
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

    private final ApplicationContext context;

    public ConnectProxy(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connect connect = method.getDeclaringClass().getAnnotation(Connect.class);
        IConnectProcessor connectProcessor = context.getBean(connect.processor());
        try {
            connectProcessor.before(proxy, method, args);
            Object execute = connectProcessor.execute(proxy, method, args);
            connectProcessor.after(proxy, method, args, execute);
            return execute;
        } catch (Exception exception) {
            connectProcessor.throwsException(proxy, method, args, exception);
            throw new BaseException(ConnectErrorEnum.CONNECT_ERROR);
        }
    }

}
