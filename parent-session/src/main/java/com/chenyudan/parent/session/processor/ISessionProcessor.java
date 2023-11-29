package com.chenyudan.parent.session.processor;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2021/5/10
 */
public interface ISessionProcessor<T> {

    void setSession(String token, T session);

    Boolean removeSession(String token);

    T getSession(String token);

}
