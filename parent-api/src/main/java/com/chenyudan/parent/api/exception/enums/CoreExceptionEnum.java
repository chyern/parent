package com.chenyudan.parent.api.exception.enums;

import com.chenyudan.parent.api.enums.IErrorEnum;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/11/28 11:13
 */
public enum CoreExceptionEnum implements IErrorEnum {
    SYSTEM_ERROR(10001, "System Exception"),
    FILE_EXIST(10002, "The file already exists:{0}"),
    FILE_NOT_FIND(10003, "The file resource does not exist:{0}"),

    FILE_NOT_IS_DIRECTORY(10004, "The file resource is not a directory:{0}"),

    DIRECTORY_IS_EMPTY(10005, "The directory is empty:{0}"),

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
