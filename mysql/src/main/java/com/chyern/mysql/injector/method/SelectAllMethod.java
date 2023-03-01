package com.chyern.mysql.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/11/3 11:57
 */
public class SelectAllMethod extends AbstractMethod {

    private static final String METHOD_NAME = "selectAll";

    protected SelectAllMethod(String methodName) {
        super(methodName);
    }

    public SelectAllMethod() {
        super(METHOD_NAME);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sqlMethod = "<script>\nSELECT * FROM %s WHERE %s\n</script>";
        String sql;
        if (tableInfo.isWithLogicDelete()) {
            TableFieldInfo logicDeleteFieldInfo = tableInfo.getLogicDeleteFieldInfo();
            String column = logicDeleteFieldInfo.getColumn();
            String logicNotDeleteValue = logicDeleteFieldInfo.getLogicNotDeleteValue();
            sql = String.format(sqlMethod, tableInfo.getTableName(), String.format("%s=#{%s}", column, logicNotDeleteValue));
        } else {
            sql = String.format(sqlMethod, tableInfo.getTableName(), "1=1");
        }
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, METHOD_NAME, sqlSource, tableInfo);

    }
}
