package com.chenyudan.parent.api.domain.request.command;

import com.chenyudan.parent.api.domain.request.Request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: 基础指令
 *
 * @author Chyern
 * @since 2023/2/1 16:09
 */
@ApiModel(description = "Description: 基础指令")
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCommand extends Request {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private String userId;

}
