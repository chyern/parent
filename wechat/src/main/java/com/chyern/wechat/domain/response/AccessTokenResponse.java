package com.chyern.wechat.domain.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:28
 */
@Data
public class AccessTokenResponse implements Serializable {

    private static final long serialVersionUID = -2662319664692065088L;

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
    /**
     * 获取到的凭证，最长为512字节
     */
    @SerializedName("access_token")
    private String accessToken;
    /**
     * 凭证的有效时间（秒）
     */
    @SerializedName("expires_in")
    private Integer expiresIn;
}
