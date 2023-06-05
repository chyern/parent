package com.chenyudan.parent.core.domain;

import lombok.Data;

import java.util.Date;

/**
 * Description: 基础DTO
 *
 * @author Chyern
 * @since 2022/12/13 14:17
 */
@Data
public class BaseBO {

    /**
     * 组织ID
     */
    private String organizationId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
