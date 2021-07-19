package com.github.chyern.connect.processor;

import com.github.chyern.common.enums.ChyernErrorEnum;
import com.github.chyern.common.exception.ChyernException;
import com.google.gson.GsonBuilder;

import java.net.URL;
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

    protected URL url;

    protected String method;

    protected Map<String, String> headers = new HashMap<>();

    protected Object body;

    protected Class respClazz;

    protected void before() {
        headers.put("Content-Type", JSON_UTF_8);
        headers.put("accept", JSON_UTF_8);
    }

    protected abstract String around() throws Exception;

    protected Object after(String result) {
        return new GsonBuilder().create().fromJson(result, respClazz);
    }

    public Object execute() {
        try {
            before();
            return after(around());
        } catch (Exception e) {
            throw new ChyernException(ChyernErrorEnum.CONNECT_RESULT_ERROR);
        }
    }

}
