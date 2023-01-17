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

    private final Integer errorCode;

    private final String errorMsg;

    private final IErrorEnum errorEnum;

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public IErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public BaseException(IErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorCode = errorEnum.getErrorCode();
        this.errorMsg = errorEnum.getErrorMsg();
        this.errorEnum = errorEnum;
    }

    public BaseException(IErrorEnum errorEnum, Object... objects) {
        super(MessageFormat.format(errorEnum.getErrorMsg(), objects));
        this.errorCode = errorEnum.getErrorCode();
        this.errorMsg = MessageFormat.format(errorEnum.getErrorMsg(), objects);
        this.errorEnum = errorEnum;
    }

}
