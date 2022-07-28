package com.github.chyern.common.exception;

import com.github.chyern.common.enums.IErrorEnum;
import lombok.Getter;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
@Getter
public class Exception extends RuntimeException {

    private final IErrorEnum errorEnum;

    public Exception(IErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorEnum = errorEnum;
    }
}
