package com.chyern.connect.processor;

import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
public interface IConnectProcessor {

    public void before(Object proxy, Method method, Object[] args) throws Throwable;

    public Object execute(Object proxy, Method method, Object[] args) throws Throwable;

    public void after(Object proxy, Method method, Object[] args, Object result) throws Throwable;

    public void throwsException(Object proxy, Method method, Object[] args, Exception exception);
}
