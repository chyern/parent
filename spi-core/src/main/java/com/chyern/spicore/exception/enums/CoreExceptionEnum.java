package com.chyern.spicore.exception.enums;

import com.chyern.spicore.enums.IErrorEnum;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/11/28 11:13
 */
public enum CoreExceptionEnum implements IErrorEnum {
    SYSTEM_ERROR(10001, "System Exception"),
    CONNECT_ERROR(10002, "The remote interface is abnormal"),
    URI_NOT_FIND(10003, "The requested page resource does not exist"),
    FILE_EXIST(10004, "The file already exists:{0}"),
    FILE_NOT_FIND(10005, "The file resource does not exist:{0}"),

    ;

    private final Integer errorCode;
    private final String errorMsg;

    CoreExceptionEnum(Integer errorCode, String errorMsg) {
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
