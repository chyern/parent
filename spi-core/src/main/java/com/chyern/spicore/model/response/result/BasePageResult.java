package com.chyern.spicore.model.response.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/2 14:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageResult<T> extends BaseResult {

    /**
     * 页数
     */
    private Long pageNo;

    /**
     * 页大小
     */
    private Long pageSize;

    /**
     * 总数
     */
    private Long total;

    /**
     * 结果
     */
    private List<T> list;
}
