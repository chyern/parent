package com.github.chyern.connect.processor;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
public interface IConnectProcessor {

    public Object execute(Object proxy, Method method, Object[] args) throws IOException;
}
