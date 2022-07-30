package com.github.chyern.common.exception;

import com.github.chyern.common.enums.IErrorEnum;
import lombok.Getter;

import java.text.MessageFormat;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
@Getter
public class CommonException extends RuntimeException {

    private final IErrorEnum errorEnum;

    public CommonException(IErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorEnum = errorEnum;
    }

    public CommonException(IErrorEnum errorEnum, Object... objects) {
        super(MessageFormat.format(errorEnum.getErrorMsg(), objects));
        this.errorEnum = errorEnum;
    }

}
