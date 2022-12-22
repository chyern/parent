package com.chyern.spicore.model;

import com.chyern.spicore.enums.IErrorEnum;
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
        response.code = errorEnum.getErrorCode();
        response.msg = errorEnum.getErrorMsg();
        response.t = System.currentTimeMillis();
        return response;
    }
}
