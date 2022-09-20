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
     * 自定义批量插入
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int insertBatch(@Param("list") List<T> list);

}
