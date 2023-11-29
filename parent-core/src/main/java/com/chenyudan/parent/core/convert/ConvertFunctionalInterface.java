package com.chenyudan.parent.core.convert;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/6/15 11:46
 */
@FunctionalInterface
public interface ConvertFunctionalInterface<T, R> {

    /**
     * convert
     */
    R convert(T t);
}
