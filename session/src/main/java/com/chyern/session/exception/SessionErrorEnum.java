package com.chyern.session.exception;

import com.chyern.core.enums.IErrorEnum;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/30 16:35
 */
public enum SessionErrorEnum implements IErrorEnum {

    WITHOUT_LOGIN(30001, "Not logged in,Please login first"),
    ;

    private final Integer errorCode;
    private final String errorMsg;

    SessionErrorEnum(Integer errorCode, String errorMsg) {
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
