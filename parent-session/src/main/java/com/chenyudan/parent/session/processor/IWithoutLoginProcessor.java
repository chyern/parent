package com.chenyudan.parent.session.processor;

import javax.servlet.http.HttpServletResponse;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/1/17 19:38
 */
public interface IWithoutLoginProcessor {

    /**
     * 设置返回信息
     *
     * @param response
     */
    default void processor(HttpServletResponse response) throws Exception {

    }
}
