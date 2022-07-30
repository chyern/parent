package com.github.chyern.connect.exception;

import com.github.chyern.common.enums.IErrorEnum;
import com.github.chyern.common.exception.CommonException;
import lombok.Getter;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
@Getter
public class ConnectException extends CommonException {

    public ConnectException(IErrorEnum errorEnum) {
        super(errorEnum);
    }

    public ConnectException(IErrorEnum errorEnum, Object... objects) {
        super(errorEnum, objects);
    }
}
