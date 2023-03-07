package com.chyern.parent.mysql.injector.constants;

import lombok.Getter;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/3/2 12:18
 */
@Getter
public enum SqlMethod {
    INSERT_BATCH("insertBatch", "<script>\nINSERT INTO %s %s VALUES %s\n</script>", "批量添加"),
    SELECT_ALL("selectAll", "<script>\nSELECT * FROM %s WHERE %s\n</script>", "全量查询"),
    ;

    SqlMethod(String name, String sql, String desc) {
        this.name = name;
        this.sql = sql;
        this.desc = desc;
    }

    /**
     * 方法名
     */
    private final String name;

    /**
     * 方法语句
     */
    private final String sql;

    /**
     * 描述
     */
    private final String desc;
}
