package com.chenyudan.parent.wechat.connect.common.domain.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccessTokenResponse extends BaseResponse {

    private static final long serialVersionUID = -2662319664692065088L;

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
