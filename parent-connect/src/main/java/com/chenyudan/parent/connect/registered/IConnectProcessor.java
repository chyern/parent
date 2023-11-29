package com.chenyudan.parent.connect.registered;

import java.lang.reflect.Method;

/**
 * Description: 连接器处理类
 *
 * @author chenyu
 * @since 2022/7/29 15:41
 */
public interface IConnectProcessor {

    /**
     * 执行前置操作
     */
    public void before(Object proxy, Method method, Object[] args) throws Throwable;

    /**
     * 执行操作
     */
    public Object execute(Object proxy, Method method, Object[] args) throws Throwable;

    /**
     * 执行后置操作
     */
    public void after(Object proxy, Method method, Object[] args, Object result) throws Throwable;

    /**
     * 异常抛出前置操作
     */
    public void throwsException(Object proxy, Method method, Object[] args, Exception exception);
}
