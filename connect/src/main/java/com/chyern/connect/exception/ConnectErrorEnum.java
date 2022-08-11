package com.chyern.connect.exception;

import com.chyern.core.enums.IErrorEnum;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
public enum ConnectErrorEnum implements IErrorEnum {

    COULD_NOT_INSTANTIATION_KEY(10001, "{0} must be instantiation"),

    CONNECT_ERROR(2000, "Connect is unsuccessful"),
    CONNECT_BODY_ERROR(2002, "It is not allowed to define two request types on a single body"),
    CONNECT_HEADER_ERROR(2003, "It is not allowed to define two request types on a single header"),
    CONNECT_HEADER_TYPE_ERROR(2003, "The header type must be map"),


    ;

    private final Integer errorCode;
    private final String errorMsg;

    ConnectErrorEnum(Integer errorCode, String errorMsg) {
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
