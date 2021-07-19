package com.github.chyern.session.strategy;


import com.github.chyern.session.model.Session;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/20
 */
public class SessionRepository implements org.springframework.session.SessionRepository<Session> {

    private final Map<String, Session> sessionMap = new HashMap<>();

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
