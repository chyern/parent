package com.chenyudan.parent.database.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/4/14 14:19
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    public RoutingDataSource(DataSource dataSource, Map<String, DataSource> dataSourceList) {
        this.setDefaultTargetDataSource(dataSource);
        this.setTargetDataSources(new HashMap<>(dataSourceList));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getLookupKey();
    }
}
