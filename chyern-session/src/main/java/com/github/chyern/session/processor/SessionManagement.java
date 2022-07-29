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
    private ISessionProcessor<T> sessionProcessor;

    public void setSession(T obj) {
        sessionProcessor.setSession(WebInterceptor.TokenThreadLocal.get(), obj);
    }

    public Boolean removeSession() {
        return sessionProcessor.removeSession(WebInterceptor.TokenThreadLocal.get());
    }

    public T getSession() {
        return sessionProcessor.getSession(WebInterceptor.TokenThreadLocal.get());
    }

}
