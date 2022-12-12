package com.chyern.mysql.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/12/12 14:14
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -166132025153260715L;

    /**
     * 页数
     */
    private Long pageNo = 1L;

    /**
     * 页大小
     */
    private Long pageSize = 10L;
}
