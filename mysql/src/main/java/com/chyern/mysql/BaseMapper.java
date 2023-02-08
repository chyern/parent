package com.chyern.mysql;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/9/19 16:14
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 批量插入
     */
    int insertBatch(@Param("list") List<T> list);


    /**
     * 查询全部
     */
    List<T> selectAll();

}
