package com.github.chyern.common.utils;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.exception.Exception;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/3/27 21:49
 */
public class AssertUtil {

    public static void isTrue(boolean b, ErrorEnum errorEnum) {
        if (!b) {
            throw new Exception(errorEnum);
        }
    }

    public static void isNull(Object obj, ErrorEnum errorEnum) {
        if (obj == null) {
            throw new Exception(errorEnum);
        }
    }

    public static void nonNull(Object obj, ErrorEnum errorEnum) {
        if (obj != null) {
            throw new Exception(errorEnum);
        }
    }

    public static <T> void isEmpty(Collection<?> collection, ErrorEnum errorEnum) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new Exception(errorEnum);
        }
    }
}
