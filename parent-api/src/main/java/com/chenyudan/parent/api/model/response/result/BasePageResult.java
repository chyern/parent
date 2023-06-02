package com.chenyudan.parent.api.model.response.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Description: 分页结果
 *
 * @author Chyern
 * @since 2023/2/2 14:47
 */
@ApiModel(description = "Description: 分页结果")
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageResult<T> extends BaseResult {

    /**
     * 页数
     */
    @ApiModelProperty("页数")
    private Long pageNo;

    /**
     * 页大小
     */
    @ApiModelProperty("页大小")
    private Long pageSize;

    /**
     * 总数
     */
    @ApiModelProperty("总数")
    private Long total;

    /**
     * 结果
     */
    @ApiModelProperty("结果")
    private List<T> list;
}
