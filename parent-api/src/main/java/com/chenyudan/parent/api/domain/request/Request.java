package com.chenyudan.parent.api.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

/**
 * Description: 请求
 *
 * @author Chyern
 * @since 2023/3/2 15:13
 */
@ApiModel(description = "Description: 请求")
@Data
public class Request implements Serializable {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = -5987707930370268639L;

    /**
     * 请求ID
     */
    @ApiModelProperty("请求ID")
    private String requestId = UUID.randomUUID().toString();

    /**
     * 请求ip
     */
    @ApiModelProperty("请求IP")
    private String requestIp;

    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String lang = Locale.CHINESE.toString();

    /**
     * 租户ID
     */
    @ApiModelProperty("租户ID")
    private String tenantId;
}
