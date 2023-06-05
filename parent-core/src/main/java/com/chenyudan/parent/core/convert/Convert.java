package com.chenyudan.parent.core.convert;

import com.chenyudan.parent.api.enums.IIntegerEnum;
import com.chenyudan.parent.api.enums.ILongEnum;
import com.chenyudan.parent.api.enums.IStringEnum;

/**
 * Description: 通用convert
 *
 * @author Chyern
 * @since 2023/1/31 17:20
 */
public interface Convert {

    default String convertEnum(IStringEnum stringEnum) {
        if (stringEnum == null) {
            return null;
        }
        return stringEnum.getCode();
    }

    default Integer convertEnum(IIntegerEnum integerEnum) {
        if (integerEnum == null) {
            return null;
        }
        return integerEnum.getCode();
    }

    default Long convertEnum(ILongEnum iLongEnum) {
        if (iLongEnum == null) {
            return null;
        }
        return iLongEnum.getCode();
    }
}
