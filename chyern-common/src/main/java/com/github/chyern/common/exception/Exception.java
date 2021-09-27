package com.github.chyern.common.exception;

import com.github.chyern.common.constant.Error;
import lombok.Getter;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
@Getter
public class Exception extends RuntimeException {

    private final Error error;

    public Exception(Error error) {
        super(error.getErrorMsg());
        this.error = error;
    }
}
