package com.chenyudan.parent.mysql.mybatis;

import com.chenyudan.parent.mysql.mybatis.domain.PageRequest;
import com.chenyudan.parent.mysql.mybatis.domain.PageResponse;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/12/13 11:54
 */
public interface BaseDao<T> {

    /**
     * 新增记录
     *
     * @param t 实体对象
     */
    int insert(T t);

    /**
     * 批量新增
     *
     * @param list 实体对象列表
     */
    int insertBatch(List<T> list);

    /**
     * 删除记录
     *
     * @param t 实体对象
     */
    int delete(T t);

    /**
     * 查询全部记录
     */
    List<T> selectAll();

    /**
     * 查询单个记录
     *
     * @param t 实体对象
     */
    T selectOne(T t);

    /**
     * 查询列表
     *
     * @param t 实体对象
     */
    List<T> selectList(T t);

    /**
     * 分页查询
     *
     * @param pageRequest 实体对象
     */
    PageResponse<T> selectPage(PageRequest<T> pageRequest);

    /**
     * 统计数量
     *
     * @param t 实体对象
     */
    Long selectCount(T t);

    /**
     * 是否存在
     *
     * @param t 实体对象
     */
    boolean exists(T t);
}
