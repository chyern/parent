package com.chenyudan.parent.database.datasource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/4/18 15:43
 */
public class DataSourceExecute {

    public static Object execute(String lookupKey, DataSourceFunctionalInterface dataSourceFunctionalInterface) throws Throwable {
        DataSourceContextHolder.setLookupKey(lookupKey);
        try {
            return dataSourceFunctionalInterface.handle();
        } finally {
            DataSourceContextHolder.remove();
        }
    }

    @FunctionalInterface
    public interface DataSourceFunctionalInterface {

        Object handle() throws Throwable;
    }
}
