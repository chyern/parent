package com.github.chyen.session.processor;

import com.github.chyen.session.model.Session;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
@Component
public class SessionManagement<T> {

    public static final String SESSION_NAME = "token";

    @Resource
    private Session session;

    @Lazy
    @Resource
    private SessionProcessor<T> sessionProcessor;

    public Boolean setSession(T obj) {
        Boolean setSession = sessionProcessor.setSession(session.getId(), obj);
        if (setSession) {
            session.setAttribute(SessionManagement.SESSION_NAME, session.getId());
            return true;
        }
        return false;
    }

    public Boolean removeSession() {
        Boolean removeSession = sessionProcessor.removeSession(session.getId());
        if (removeSession) {
            session.removeAttribute(SessionManagement.SESSION_NAME);
            return true;
        }
        return false;
    }

    public T getSession() {
        return sessionProcessor.getSession(session.getId());
    }

}
