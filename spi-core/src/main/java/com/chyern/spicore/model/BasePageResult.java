package com.chyern.spicore.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/2 14:47
 */
@Data
public class BasePageResult<T> implements Serializable {
    private static final long serialVersionUID = 6823851364026718111L;

    /**
     * 请求id
     */
    private String requestId;

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
