package com.chyern.mysql.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.commons.lang3.StringUtils;
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

    private static final String METHOD_NAME = "insertBatch";

    protected InsertBatchMethod(String methodName) {
        super(methodName);
    }

    public InsertBatchMethod() {
        super(METHOD_NAME);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>\nINSERT INTO %s %s VALUES %s\n</script>";

        LinkedHashMap<String, String> tableFieldMap = getTableFieldMap(tableInfo);
        final String fieldSql = prepareFieldSql(tableFieldMap);
        final String valueSql = prepareValuesSql(tableFieldMap);
        final String sqlResult = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, METHOD_NAME, sqlSource, new NoKeyGenerator(), null, null);
    }

    private LinkedHashMap<String, String> getTableFieldMap(TableInfo tableInfo) {
        LinkedHashMap<String, String> tableFieldMap = new LinkedHashMap<>();

        if (StringUtils.isNotBlank(tableInfo.getKeyColumn()) && StringUtils.isNotBlank(tableInfo.getKeyProperty())) {
            tableFieldMap.put(tableInfo.getKeyColumn(), tableInfo.getKeyProperty());
        }

        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        fieldList.forEach(field -> {
            tableFieldMap.put(field.getColumn(), field.getProperty());
        });
        return tableFieldMap;
    }

    private String prepareFieldSql(LinkedHashMap<String, String> tableFieldMap) {
        List<String> fieldColumns = new ArrayList<>(tableFieldMap.keySet());
        return "(" + StringUtils.join(fieldColumns, ",") + ")";
    }

    private String prepareValuesSql(LinkedHashMap<String, String> tableFieldMap) {
        String begin = "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">";
        String end = "</foreach>";

        List<String> fieldPropertys = tableFieldMap.values().stream().map(s -> "#{item." + s + "}").collect(Collectors.toList());

        return begin + StringUtils.join(fieldPropertys, ",") + end;
    }
}
