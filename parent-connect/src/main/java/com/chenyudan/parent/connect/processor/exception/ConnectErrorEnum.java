package com.chenyudan.parent.connect.processor.exception;

import com.chenyudan.parent.api.enums.IErrorEnum;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2021/7/19
 */
public enum ConnectErrorEnum implements IErrorEnum {

    COULD_NOT_INSTANTIATION_KEY(11001, "{0} must be instantiation"),
    CONNECT_BODY_ERROR(11002, "It is not allowed to define two request types on a single body"),
    CONNECT_HEADER_TYPE_ERROR(11003, "The header type must be map"),


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
