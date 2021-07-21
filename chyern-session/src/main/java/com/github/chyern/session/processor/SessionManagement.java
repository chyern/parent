package com.github.chyern.session.processor;

import com.github.chyern.session.interceptor.WebInterceptor;

import javax.annotation.Resource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
public class SessionManagement<T> {

    @Resource
    private SessionProcessor<T> sessionProcessor;

    public Boolean setSession(T obj) {
        return sessionProcessor.setSession(WebInterceptor.getSessionId(), obj);
    }

    public Boolean removeSession() {
        return sessionProcessor.removeSession(WebInterceptor.getSessionId());
    }

    public T getSession() {
        return sessionProcessor.getSession(WebInterceptor.getSessionId());
    }

}
