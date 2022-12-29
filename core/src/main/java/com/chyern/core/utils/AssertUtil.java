package com.chyern.core.utils;

import com.chyern.spicore.enums.IErrorEnum;
import com.chyern.spicore.exception.BaseException;
import org.springframework.util.CollectionUtils;

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
