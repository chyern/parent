package com.chenyudan.parent.api.domain.response.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 基础结果
 *
 * @author chenyu
 * @since 2023/2/2 14:41
 */
@ApiModel(description = "Description: 基础结果")
@Data
public class BaseResult implements Serializable {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 5604336341064298376L;

    /**
     * 请求id
     */
    @ApiModelProperty("请求ID")
    private String requestId;

    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String lang;

    /**
     * 租户ID
     */
    @ApiModelProperty("租户ID")
    private String tenantId;

}
