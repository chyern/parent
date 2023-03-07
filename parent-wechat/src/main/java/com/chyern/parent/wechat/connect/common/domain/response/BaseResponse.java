package com.chyern.parent.wechat.connect.common.domain.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/17 19:00
 */
@Data
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = -1407329563489413024L;

    /**
     * 出错返回码，为0表示成功，非0表示调用失败
     */
    @SerializedName("errcode")
    private Integer errcode;
    /**
     * 返回码提示语
     */
    @SerializedName("errmsg")
    private String errmsg;
}
