package com.chenyudan.parent.api.domain.response;

import com.chenyudan.parent.api.enums.IErrorEnum;
import com.chenyudan.parent.api.exception.BaseException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 响应
 *
 * @author chenyu
 * @since 2021/9/27
 */
@ApiModel(description = "Description: 返回")
@Data
public class Response<T> implements Serializable {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 7022238929369223121L;

    /**
     * 是否成功
     */
    @ApiModelProperty("是否成功")
    private Boolean success;

    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    private Integer code;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String msg;

    /**
     * 结果
     */
    @ApiModelProperty("结果")
    private T result;

    /**
     * 时间
     */
    @ApiModelProperty("时间")
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
