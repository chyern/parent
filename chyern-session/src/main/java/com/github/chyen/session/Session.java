package com.github.chyen.session;

import org.springframework.session.ExpiringSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
public class Session implements ExpiringSession {

    private final String id;

    private final Map<String, Object> map;

    public Session() {
        this.id = UUID.randomUUID().toString();
        this.map = new HashMap<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public <T> T getAttribute(String attributeName) {
        return (T) map.get(attributeName);
    }

    @Override
    public Set<String> getAttributeNames() {
        return map.keySet();
    }

    @Override
    public void setAttribute(String attributeName, Object attributeValue) {
        map.put(attributeName, attributeValue);
    }

    @Override
    public void removeAttribute(String attributeName) {
        map.remove(attributeName);
    }

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public void setLastAccessedTime(long lastAccessedTime) {

    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public void setMaxInactiveIntervalInSeconds(int interval) {

    }

    @Override
    public int getMaxInactiveIntervalInSeconds() {
        return 0;
    }

    @Override
    public boolean isExpired() {
        return false;
    }
}
