package com.github.chyern.session.processor;

import com.github.chyern.session.interceptor.WebInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
@Component
public class SessionManagement<T> {

    @Autowired
    private SessionProcessor<T> sessionProcessor;

    public Boolean setSession(T obj) {
        return sessionProcessor.setSession(WebInterceptor.getToken(), obj);
    }

    public Boolean removeSession() {
        return sessionProcessor.removeSession(WebInterceptor.getToken());
    }

    public T getSession() {
        return sessionProcessor.getSession(WebInterceptor.getToken());
    }

}
