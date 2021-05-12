package com.github.chyen.session.processor;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
    private HttpSession session;

    @Resource
    private SessionProcessor<T> sessionHandler;

    public Boolean setSession(T obj) {
        Boolean setSession = sessionHandler.setSession(session.getId(), obj);
        if (setSession) {
            session.setAttribute(SessionManagement.SESSION_NAME, session.getId());
            return true;
        }
        return false;
    }

    public Boolean removeSession() {
        Boolean removeSession = sessionHandler.removeSession(session.getId());
        if (removeSession) {
            session.removeAttribute(SessionManagement.SESSION_NAME);
            return true;
        }
        return false;
    }

    public T getSession() {
        return sessionHandler.getSession(session.getId());
    }

}
