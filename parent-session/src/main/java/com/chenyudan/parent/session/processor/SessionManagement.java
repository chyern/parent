package com.chenyudan.parent.session.processor;

import com.chenyudan.parent.session.interceptor.WebInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2021/5/10
 */
@Component
public class SessionManagement<T> {

    @Resource
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
