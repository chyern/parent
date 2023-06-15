package com.chenyudan.parent.core.convert;

import com.chenyudan.parent.api.enums.IEnum;

import java.util.List;

/**
 * Description: 通用convert
 *
 * @author Chyern
 * @since 2023/1/31 17:20
 */
public interface BaseConvert<T, R> {

    default String convertStringEnum(IEnum<String> stringEnum) {
        if (stringEnum == null) {
            return null;
        }
        return stringEnum.getCode();
    }

    default Integer convertIntegerEnum(IEnum<Integer> integerEnum) {
        if (integerEnum == null) {
            return null;
        }
        return integerEnum.getCode();
    }

    default Long convertLongEnum(IEnum<Long> iLongEnum) {
        if (iLongEnum == null) {
            return null;
        }
        return iLongEnum.getCode();
    }

    T convertRtoT(R r);

    List<T> convertRtoT(List<R> r);

    R convertTtoR(T t);

    List<R> convertTtoR(List<T> t);
}
