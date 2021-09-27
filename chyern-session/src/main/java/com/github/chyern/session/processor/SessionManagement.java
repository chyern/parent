package com.github.chyern.session.processor;

import com.github.chyern.common.utils.ContextUtil;
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

    @Autowired(required = false)
    private ISessionProcessor<T> sessionProcessor;

    public Boolean setSession(T obj) {
        return sessionProcessor.setSession(ContextUtil.get().getToken(), obj);
    }

    public Boolean removeSession() {
        return sessionProcessor.removeSession(ContextUtil.get().getToken());
    }

    public T getSession() {
        return sessionProcessor.getSession(ContextUtil.get().getToken());
    }

}
