package com.github.chyern.connect.handler;

import com.github.chyern.connect.Exception.ConnectException;
import com.google.gson.GsonBuilder;

import java.net.URL;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
public abstract class AbstractConnectHandler {

    private static final String JSON_UTF_8 = "application/json;charset=utf-8";

    protected URL url;

    protected String method;

    protected Map<String, String> headers;

    protected Object body;

    protected Class respClazz;

    protected void before() throws Exception {
        headers.put("Content-Type", JSON_UTF_8);
        headers.put("accept", JSON_UTF_8);
    }

    protected abstract String around() throws Exception;

    protected Object after(String result) throws Exception {
        return new GsonBuilder().create().fromJson(result, respClazz);
    }

    public Object execute() throws ConnectException {
        try {
            before();
            return after(around());
        } catch (Exception e) {
            throw new ConnectException(e.getMessage());
        }
    }

}
