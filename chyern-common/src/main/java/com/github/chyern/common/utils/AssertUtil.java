package com.github.chyern.common.utils;

import com.github.chyern.common.enums.IErrorEnum;
import com.github.chyern.common.exception.CommonException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/3/27 21:49
 */
public class AssertUtil {

    public static void isTrue(boolean b, IErrorEnum errorEnum) {
        if (!b) {
            throw new CommonException(errorEnum);
        }
    }

    public static void isTrue(boolean b, IErrorEnum errorEnum, Object... objects) {
        if (!b) {
            throw new CommonException(errorEnum, objects);
        }
    }

    public static void isNull(Object obj, IErrorEnum errorEnum) {
        if (obj == null) {
            throw new CommonException(errorEnum);
        }
    }

    public static void nonNull(Object obj, IErrorEnum errorEnum) {
        if (obj != null) {
            throw new CommonException(errorEnum);
        }
    }

    public static <T> void isEmpty(Collection<?> collection, IErrorEnum errorEnum) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new CommonException(errorEnum);
        }
    }
}
