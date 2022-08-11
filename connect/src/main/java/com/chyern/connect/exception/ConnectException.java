package com.chyern.connect.exception;

import com.chyern.core.enums.IErrorEnum;
import com.chyern.core.exception.CommonException;
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
