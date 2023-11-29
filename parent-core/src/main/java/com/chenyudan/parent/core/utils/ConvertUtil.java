package com.chenyudan.parent.core.utils;

import java.math.BigDecimal;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/6/30 11:00
 */
public class ConvertUtil {

    public static double convertDouble(BigDecimal bigDecimal) {
        return bigDecimal == null ? 0 : bigDecimal.doubleValue();
    }

    public static boolean convertBoolean(String str) {
        return Boolean.getBoolean(str);
    }

    public static int convertInt(Long l) {
        return l == null ? 0 : l.intValue();
    }
}
