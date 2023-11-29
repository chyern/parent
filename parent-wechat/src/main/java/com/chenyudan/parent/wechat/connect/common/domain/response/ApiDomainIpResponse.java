package com.chenyudan.parent.wechat.connect.common.domain.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/1/11 19:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApiDomainIpResponse extends BaseResponse {

    private static final long serialVersionUID = 8708651270647844516L;

    /**
     * 企业微信服务器IP段
     */
    @SerializedName("ip_list")
    private List<String> ipList;
}
