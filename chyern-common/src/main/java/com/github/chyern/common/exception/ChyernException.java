package com.github.chyern.common.exception;

import com.github.chyern.common.enums.ChyernErrorEnum;
import lombok.Getter;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
@Getter
public class ChyernException extends RuntimeException {

    private final Integer code;

    private final String message;

    public ChyernException(ChyernErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }
}
