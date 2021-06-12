package com.github.chyern.session.model;

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
public class Session implements org.springframework.session.Session {

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
}
