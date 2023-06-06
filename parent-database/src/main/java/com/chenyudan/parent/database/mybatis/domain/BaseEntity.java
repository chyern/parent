package com.chenyudan.parent.database.mybatis.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.chenyudan.parent.core.constant.Constant;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;


/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/11/29 14:57
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 8805086841629261002L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "bigint COMMENT 'ID'")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 组织ID
     */
    @Column(name = "organization_id", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '组织ID'")
    private String organizationId;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, columnDefinition = "timestamp COMMENT '创建时间'")
    private Date createTime;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time", nullable = false, columnDefinition = "timestamp COMMENT '更新时间'")
    private Date updateTime;

    /**
     * 是否删除(0表示未删除)
     */
    @TableLogic(value = "0", delval = "1")
    @Column(name = "is_delete", nullable = false, columnDefinition = "int(1) default '0' COMMENT '更新时间'")
    private Integer isDelete = Constant.IS_DELETE_N;
}
