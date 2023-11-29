package com.chenyudan.parent.api.exception;

import com.chenyudan.parent.api.enums.IErrorEnum;
import com.chenyudan.parent.api.utils.TranslationUtil;
import lombok.Getter;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2021/7/19
 */
@Getter
public class BaseException extends RuntimeException {

    private final Integer errorCode;

    private final String errorMsg;

    public BaseException(Integer errorCode, String errorMsg) {
        super(errorCode.toString());
        this.errorCode = errorCode;
        this.errorMsg = TranslationUtil.getMessage(errorCode.toString(), errorMsg);
    }

    public BaseException(Integer errorCode, String errorMsg, Object... objects) {
        super(errorCode.toString());
        this.errorCode = errorCode;
        this.errorMsg = TranslationUtil.getMessage(errorCode.toString(), errorMsg, objects);
    }

    public BaseException(IErrorEnum errorEnum) {
        super(errorEnum.getErrorCode().toString());
        Integer errorCode = errorEnum.getErrorCode();
        String errorMsg = errorEnum.getErrorMsg();
        this.errorCode = errorCode;
        this.errorMsg = TranslationUtil.getMessage(errorCode.toString(), errorMsg);
    }

    public BaseException(IErrorEnum errorEnum, Object... objects) {
        super(errorEnum.getErrorCode().toString());
        Integer errorCode = errorEnum.getErrorCode();
        String errorMsg = errorEnum.getErrorMsg();
        this.errorCode = errorCode;
        this.errorMsg = TranslationUtil.getMessage(errorCode.toString(), errorMsg, objects);
    }

}
