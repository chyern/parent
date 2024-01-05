package com.chenyudan.parent.database.mybatis.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import java.util.Arrays;
import java.util.List;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/2/8 22:07
 */
public class DefaultInterceptor implements Interceptor {

    @Override
    public org.apache.ibatis.plugin.Interceptor[] resolveInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.setInterceptors(Arrays.asList(new PaginationInnerInterceptor(DbType.MYSQL)));
        return new org.apache.ibatis.plugin.Interceptor[]{mybatisPlusInterceptor};
    }
}
