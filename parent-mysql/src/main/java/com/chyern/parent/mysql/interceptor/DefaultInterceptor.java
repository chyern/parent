package com.chyern.parent.mysql.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/8 22:07
 */
public class DefaultInterceptor implements Interceptor {

    @Override
    public org.apache.ibatis.plugin.Interceptor[] resolveInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.setInterceptors(List.of(new PaginationInnerInterceptor(DbType.MYSQL)));
        return new org.apache.ibatis.plugin.Interceptor[]{mybatisPlusInterceptor};
    }
}
