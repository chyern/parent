package com.github.chyern.common.model;

import com.github.chyern.common.constant.Error;
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

    public static <T> Response buildSuccess(T data) {
        Response response = new Response();
        response.success = true;
        response.result = data;
        response.t = System.currentTimeMillis();
        return response;
    }

    public static Response buildFailure(Error error) {
        Response response = new Response();
        response.code = error.getErrorCode();
        response.msg = error.getErrorMsg();
        response.t = System.currentTimeMillis();
        return response;
    }
}
