package com.chyern.connect.handler;

import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
public abstract class AbstractConnectHandler {

    protected String uri;

    protected String method;

    protected Map<String, String> headers;

    protected Map<String,Object> query;

    protected Object body;

    protected Class respClazz;

    public abstract Object execute() throws Exception;

}
