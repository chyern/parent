package com.chyern.mysql;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/9/19 16:11
 */
public class CustomizedSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new InsertBatchMethod());
        return methodList;
    }
}
