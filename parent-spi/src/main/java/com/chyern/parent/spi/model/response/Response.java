package com.chyern.parent.spi.model.response;

import com.chyern.parent.spi.enums.IErrorEnum;
import com.chyern.parent.spi.exception.BaseException;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/9/27
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 7022238929369223121L;

    private Boolean success;

    private Integer code;

    private String msg;

    private T result;

    private Long t;

    public static <T> Response<T> buildSuccess(T data) {
        Response<T> response = new Response<>();
        response.success = true;
        response.result = data;
        response.t = System.currentTimeMillis();
        return response;
    }

    public static <T> Response<T> buildFailure(IErrorEnum errorEnum) {
        Response<T> response = new Response<>();
        response.success = false;
        response.code = errorEnum.getErrorCode();
        response.msg = errorEnum.getErrorMsg();
        response.t = System.currentTimeMillis();
        return response;
    }

    public static <T,R extends BaseException> Response<T> buildFailure(R baseException) {
        Response<T> response = new Response<>();
        response.success = false;
        response.code = baseException.getErrorCode();
        response.msg = baseException.getErrorMsg();
        response.t = System.currentTimeMillis();
        return response;
    }

    public static <T> Response<T> buildFailure(Integer errorCode, String errorMsg) {
        Response<T> response = new Response<>();
        response.success = false;
        response.code = errorCode;
        response.msg = errorMsg;
        response.t = System.currentTimeMillis();
        return response;
    }
}
