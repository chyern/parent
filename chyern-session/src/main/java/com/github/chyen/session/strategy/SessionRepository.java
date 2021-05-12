package com.github.chyen.session.strategy;


import com.github.chyen.session.model.Session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
public class SessionRepository implements org.springframework.session.SessionRepository<Session> {

    private final ConcurrentMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    @Override
    public Session createSession() {
        return new Session();
    }

    @Override
    public void save(Session session) {
        sessionMap.put(session.getId(), session);
    }

    @Override
    public Session getSession(String id) {
        return sessionMap.get(id);
    }

    @Override
    public void delete(String id) {
        sessionMap.remove(id);
    }
}
