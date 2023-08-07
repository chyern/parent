package com.chenyudan.parent.database.mybatis.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.chenyudan.parent.core.utils.StringUtil;
import com.chenyudan.parent.database.mybatis.injector.constants.SqlMethod;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/9/19 16:09
 */
public class InsertBatchMethod extends AbstractMethod {

    public InsertBatchMethod() {
        super(SqlMethod.INSERT_BATCH.getName());
    }

    public InsertBatchMethod(String methodName) {
        super(methodName);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.INSERT_BATCH;
        String sql = sqlMethod.getSql();

        LinkedHashMap<String, String> tableFieldMap = getTableFieldMap(tableInfo);
        final String fieldSql = prepareFieldSql(tableFieldMap);
        final String valueSql = prepareValuesSql(tableFieldMap);
        final String sqlResult = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, sqlMethod.getName(), sqlSource, new NoKeyGenerator(), null, null);
    }

    private static LinkedHashMap<String, String> getTableFieldMap(TableInfo tableInfo) {
        LinkedHashMap<String, String> tableFieldMap = new LinkedHashMap<>();

        if (StringUtil.isNotBlank(tableInfo.getKeyColumn()) && StringUtil.isNotBlank(tableInfo.getKeyProperty())) {
            tableFieldMap.put(tableInfo.getKeyColumn(), tableInfo.getKeyProperty());
        }

        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        fieldList.forEach(field -> tableFieldMap.put(field.getColumn(), field.getProperty()));
        return tableFieldMap;
    }

    private static String prepareFieldSql(LinkedHashMap<String, String> tableFieldMap) {
        List<String> fieldColumns = new ArrayList<>(tableFieldMap.keySet());
        return "(" + StringUtil.join(fieldColumns, ",") + ")";
    }

    private static String prepareValuesSql(LinkedHashMap<String, String> tableFieldMap) {
        String begin = "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">";
        String end = "</foreach>";

        List<String> fieldPropertys = tableFieldMap.values().stream().map(s -> "#{item." + s + "}").collect(Collectors.toList());

        return begin + StringUtil.join(fieldPropertys, ",") + end;
    }
}
