package com.chyern.spicore.exception;

import com.chyern.spicore.enums.IErrorEnum;

import java.text.MessageFormat;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
public class BaseException extends RuntimeException {

    private final IErrorEnum errorEnum;

    public IErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public BaseException(IErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorEnum = errorEnum;
    }

    public BaseException(IErrorEnum errorEnum, Object... objects) {
        super(MessageFormat.format(errorEnum.getErrorMsg(), objects));
        this.errorEnum = errorEnum;
    }

}
