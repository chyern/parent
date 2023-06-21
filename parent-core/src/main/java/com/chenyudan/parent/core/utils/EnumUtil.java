package com.chenyudan.parent.core.utils;

import com.chenyudan.parent.api.enums.IEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 枚举工具类
 *
 * @author Chyern
 * @since 2023/6/7 11:42
 */
public class EnumUtil {

    public static <T> Map<T, String> toMap(IEnum<T>[] enums) {
        Map<T, String> map = new HashMap<>();
        if (enums == null) {
            return map;
        }

        for (IEnum<T> anEnum : enums) {
            map.put(anEnum.getCode(), anEnum.getDesc());
        }
        return map;
    }

    public static <T> IEnum<T> getBy(IEnum<T>[] enums, T code) {
        if (enums == null) {
            return null;
        }

        for (IEnum<T> anEnum : enums) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return null;
    }

}
