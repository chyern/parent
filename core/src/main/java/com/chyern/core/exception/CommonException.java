package com.chyern.core.exception;

import com.chyern.core.enums.IErrorEnum;

import java.text.MessageFormat;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
public class CommonException extends RuntimeException {

    private final IErrorEnum errorEnum;

    public IErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public CommonException(IErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorEnum = errorEnum;
    }

    public CommonException(IErrorEnum errorEnum, Object... objects) {
        super(MessageFormat.format(errorEnum.getErrorMsg(), objects));
        this.errorEnum = errorEnum;
    }

}
