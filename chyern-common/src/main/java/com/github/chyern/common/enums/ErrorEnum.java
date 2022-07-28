package com.github.chyern.common.enums;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
public enum ErrorEnum implements IErrorEnum {

    WITHOUT_LOGIN(1000, "without login"),
    WITHOUT_PERMISSION(1001, "without permission"),


    CONNECT_URL_ERROR(2000, "Could not bind the URL"),
    CONNECT_METHOD_ERROR(2001, "It is not a single method"),
    CONNECT_PATH_ERROR(2002, "Could not bind the path"),
    CONNECT_BODY_ERROR(2003, "It is not allowed to define two request types on a single body"),
    CONNECT_RESULT_ERROR(2004, "Connect is unsuccessful"),
    ;

    private final Integer errorCode;
    private final String errorMsg;

    ErrorEnum(Integer errorCode, String errorMsg) {
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
