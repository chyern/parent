package com.chyern.spicore.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/2 14:41
 */
@Data
public class BaseResult implements Serializable {
    private static final long serialVersionUID = 5604336341064298376L;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
