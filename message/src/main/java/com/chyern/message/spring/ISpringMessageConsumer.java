package com.chyern.message.spring;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/10 14:52
 */
public interface ISpringMessageConsumer<T> {

    void handle(T t);
}
