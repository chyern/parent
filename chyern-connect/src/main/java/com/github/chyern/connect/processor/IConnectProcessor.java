package com.github.chyern.connect.processor;

import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
public interface IConnectProcessor {

    public void before(Object proxy, Method method, Object[] args) throws Exception;

    public Object execute(Object proxy, Method method, Object[] args) throws Exception;

    public void after(Object proxy, Method method, Object[] args, Object result) throws Exception;

    public void throwsException(Object proxy, Method method, Object[] args, Exception exception);
}
