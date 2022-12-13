package com.chyern.mysql;

import com.chyern.mysql.domain.PageResponse;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/12/13 11:54
 */
public interface BaseDao<T> {

    int insert(T t);

    int insertBatch(List<T> list);

    int delete(T t);

    List<T> selectAll();

    T selectOne(T t);

    List<T> selectList(T t);

    PageResponse<T> selectPage(Long pageNo, Long pageSize, T t);

    Integer selectCount(T t);
}
