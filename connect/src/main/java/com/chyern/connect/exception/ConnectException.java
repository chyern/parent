package com.chyern.connect.exception;

import com.chyern.spicore.enums.IErrorEnum;
import com.chyern.spicore.exception.BaseException;
import lombok.Getter;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/19
 */
@Getter
public class ConnectException extends BaseException {

    public ConnectException(IErrorEnum errorEnum) {
        super(errorEnum);
    }

    public ConnectException(IErrorEnum errorEnum, Object... objects) {
        super(errorEnum, objects);
    }
}
