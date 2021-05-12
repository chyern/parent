package com.github.chyen.session;

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
    private SessionHandler<T> sessionHandler;

    public Boolean setSession(T obj) {
        sessionHandler.setSession(session.getId(), obj);
        session.setAttribute(SessionManagement.SESSION_NAME, session.getId());
        return true;
    }

    public Boolean removeSession() {
        sessionHandler.removeSession(session.getId());
        session.removeAttribute(SessionManagement.SESSION_NAME);
        return true;
    }

    public T getSession() {
        return sessionHandler.getSession(session.getId());
    }

}
