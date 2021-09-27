package com.github.chyern.session.processor;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
public interface ISessionProcessor<T> {

    Boolean setSession(String sessionId, T session);

    Boolean removeSession(String sessionId);

    T getSession(String sessionId);

}
