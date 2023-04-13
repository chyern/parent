package com.chyern.parent.api.model.response;

import com.chyern.parent.api.enums.IErrorEnum;
import com.chyern.parent.api.exception.BaseException;
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
        response.t = System.currentTimeMillis();
        response.success = true;
        response.result = data;
        return response;
    }

    public static <T> Response<T> buildFailure(Integer errorCode, String errorMsg) {
        Response<T> response = new Response<>();
        response.t = System.currentTimeMillis();
        response.success = false;
        response.code = errorCode;
        response.msg = errorMsg;
        return response;
    }

    public static <T> Response<T> buildFailure(IErrorEnum errorEnum) {
        return buildFailure(errorEnum.getErrorCode(), errorEnum.getErrorMsg());
    }

    public static <T, R extends BaseException> Response<T> buildFailure(R baseException) {
        return buildFailure(baseException.getErrorCode(), baseException.getErrorMsg());
    }
}
