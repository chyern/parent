package com.chenyudan.parent.database.datasource;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/4/14 14:59
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getLookupKey() {
        return threadLocal.get();
    }

    public static void setLookupKey(String lookupKey) {
        threadLocal.set(lookupKey);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
