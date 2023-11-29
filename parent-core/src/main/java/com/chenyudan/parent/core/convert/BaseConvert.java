package com.chenyudan.parent.core.convert;

import com.chenyudan.parent.api.enums.IEnum;

import java.util.List;

/**
 * Description: 通用convert
 *
 * @author chenyu
 * @since 2023/1/31 17:20
 */
public interface BaseConvert<T, R> {

    default String convertStringEnum(IEnum<String> stringEnum) {
        return stringEnum == null ? null : stringEnum.getCode();
    }

    default Integer convertIntegerEnum(IEnum<Integer> integerEnum) {
        return integerEnum == null ? null : integerEnum.getCode();
    }

    default Long convertLongEnum(IEnum<Long> iLongEnum) {
        return iLongEnum == null ? null : iLongEnum.getCode();
    }

    T toT(R r);

    List<T> toT(List<R> r);

    R toR(T t);

    List<R> toR(List<T> t);
}
