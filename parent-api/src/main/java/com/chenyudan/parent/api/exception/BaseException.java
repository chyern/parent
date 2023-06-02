package com.chenyudan.parent.api.exception;

import com.chenyudan.parent.api.enums.IErrorEnum;

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

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public BaseException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BaseException(Integer errorCode, String errorMsg, Object... objects) {
        super(MessageFormat.format(errorMsg, objects));
        this.errorCode = errorCode;
        this.errorMsg = MessageFormat.format(errorMsg, objects);
    }

    public BaseException(IErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorCode = errorEnum.getErrorCode();
        this.errorMsg = errorEnum.getErrorMsg();
    }

    public BaseException(IErrorEnum errorEnum, Object... objects) {
        super(MessageFormat.format(errorEnum.getErrorMsg(), objects));
        this.errorCode = errorEnum.getErrorCode();
        this.errorMsg = MessageFormat.format(errorEnum.getErrorMsg(), objects);
    }

}
