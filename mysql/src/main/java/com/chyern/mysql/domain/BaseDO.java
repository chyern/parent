package com.chyern.mysql.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/11/29 14:57
 */
@Data
public class BaseDO implements Serializable {
    private static final long serialVersionUID = 8805086841629261002L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 是否删除(0表示未删除)
     */
    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;
}