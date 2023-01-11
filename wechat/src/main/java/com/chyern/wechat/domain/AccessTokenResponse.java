package com.chyern.wechat.domain;

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

    private Integer errcode;

    private String errmsg;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private Integer expiresIn;
}
