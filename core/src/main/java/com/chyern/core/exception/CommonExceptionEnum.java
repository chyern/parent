package com.chyern.core.exception;

import com.chyern.core.enums.IErrorEnum;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/11/28 11:13
 */
public enum CommonExceptionEnum implements IErrorEnum {
    FILE_EXIST(10001, "file:{0} is exist"),

    ;

    private final Integer errorCode;
    private final String errorMsg;

    CommonExceptionEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
