package com.chenyudan.parent.database.mybatis.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.chenyudan.parent.database.mybatis.injector.constants.SqlMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/11/3 11:57
 */
public class SelectAllMethod extends AbstractMethod {

    public SelectAllMethod() {
        super(SqlMethod.SELECT_ALL.getName());
    }

    public SelectAllMethod(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_ALL;
        String sql = sqlMethod.getSql();
        String whereFormat = "1=1";
        if (tableInfo.isWithLogicDelete()) {
            TableFieldInfo logicDeleteFieldInfo = tableInfo.getLogicDeleteFieldInfo();
            whereFormat = String.format("%s=#{%s}", logicDeleteFieldInfo.getColumn(), logicDeleteFieldInfo.getLogicNotDeleteValue());
        }
        sql = String.format(sql, tableInfo.getTableName(), whereFormat);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, sqlMethod.getName(), sqlSource, tableInfo);

    }
}
