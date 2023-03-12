package com.chyern.parent.api.exception.enums;

import com.chyern.parent.api.enums.IErrorEnum;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
public enum ConnectErrorEnum implements IErrorEnum {

    COULD_NOT_INSTANTIATION_KEY(11001, "{0} must be instantiation"),

    CONNECT_ERROR(11002, "Connect is unsuccessful"),
    CONNECT_BODY_ERROR(11003, "It is not allowed to define two request types on a single body"),
    CONNECT_HEADER_TYPE_ERROR(11005, "The header type must be map"),


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
