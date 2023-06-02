package com.chenyudan.parent.core.utils;

import com.chenyudan.parent.api.enums.IErrorEnum;
import com.chenyudan.parent.api.exception.BaseException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/3/27 21:49
 */
public class AssertUtil {

    public static void isTrue(boolean b, IErrorEnum errorEnum, Object... objects) {
        if (!b) {
            throw new BaseException(errorEnum, objects);
        }
    }

    public static void isNull(Object obj, IErrorEnum errorEnum, Object... objects) {
        if (obj == null) {
            throw new BaseException(errorEnum, objects);
        }
    }

    public static <T> void isEmpty(Collection<?> collection, IErrorEnum errorEnum, Object... objects) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BaseException(errorEnum, objects);
        }
    }
}
