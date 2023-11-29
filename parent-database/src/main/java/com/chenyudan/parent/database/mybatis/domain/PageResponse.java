package com.chenyudan.parent.database.mybatis.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2022/12/12 14:14
 */
@Data
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = -166132025153260715L;

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
