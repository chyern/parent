package com.chyern.wechat.connect.common.domain.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 19:23
 */
@NoArgsConstructor
@Data
public class ApiDomainIpResponse implements Serializable {

    private static final long serialVersionUID = 8708651270647844516L;

    /**
     * 企业微信服务器IP段
     */
    @SerializedName("ip_list")
    private List<String> ipList;
    /**
     * 错误码，0表示成功，非0表示调用失败
     */
    @SerializedName("errcode")
    private Integer errcode;
    /**
     * 错误信息，调用失败会有相关的错误信息返回
     */
    @SerializedName("errmsg")
    private String errmsg;
}
