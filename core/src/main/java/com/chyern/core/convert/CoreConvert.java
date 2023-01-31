package com.chyern.core.convert;

import com.chyern.spicore.enums.IIntegerEnum;
import com.chyern.spicore.enums.IStringEnum;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/31 17:20
 */
public interface CoreConvert {

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
}
