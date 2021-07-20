package com.github.chyern.connect.processor;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
public abstract class AbstractConnectProcessor {

    private static final String JSON_UTF_8 = "application/json;charset=utf-8";

    protected URI url;

    protected String method;

    protected Map<String, String> headers = new HashMap<>();

    protected Object body;

    protected Class respClazz;

    protected void before() {
        headers.put("Content-Type", JSON_UTF_8);
        headers.put("accept", JSON_UTF_8);
    }

    protected abstract <T> T around();

    protected abstract <T> T after(Object obj);

    public Object execute() {
        before();
        return after(around());
    }

}
