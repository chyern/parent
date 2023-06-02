package com.chenyudan.parent.mysql.mybatis.interceptor;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/8 21:48
 */
public interface Interceptor {

    org.apache.ibatis.plugin.Interceptor[] resolveInterceptor();

}
