package com.github.chyern.permission.exception;

import com.github.chyern.common.enums.IErrorEnum;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/30 16:35
 */
public enum PermissionErrorEnum implements IErrorEnum {

    WITHOUT_PERMISSION(20001, "Permission to illegal"),
    ;

    private final Integer errorCode;
    private final String errorMsg;

    PermissionErrorEnum(Integer errorCode, String errorMsg) {
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
