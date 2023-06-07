package com.chenyudan.parent.core.convert;

import com.chenyudan.parent.api.enums.IEnum;

/**
 * Description: 通用convert
 *
 * @author Chyern
 * @since 2023/1/31 17:20
 */
public interface BaseConvert {

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
}
