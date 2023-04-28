package com.chyern.parent.api.model.request.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: 分页指令
 *
 * @author Chyern
 * @since 2023/2/1 16:21
 */
@ApiModel(description = "Description: 分页指令")
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageCommand extends BaseCommand {

    /**
     * 页数
     */
    @ApiModelProperty("页数")
    private Long pageNo = 1L;

    /**
     * 页大小
     */
    @ApiModelProperty("页大小")
    private Long pageSize = 10L;
}
