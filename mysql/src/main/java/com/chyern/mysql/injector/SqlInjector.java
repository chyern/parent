package com.chyern.mysql.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.chyern.mysql.injector.method.InsertBatchMethod;
import com.chyern.mysql.injector.method.SelectAllMethod;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/9/19 16:11
 */
public class SqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new InsertBatchMethod());
        methodList.add(new SelectAllMethod());
        return methodList;
    }
}
