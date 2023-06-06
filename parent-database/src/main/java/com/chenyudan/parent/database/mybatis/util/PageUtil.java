package com.chenyudan.parent.database.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chenyudan.parent.database.mybatis.domain.PageResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/12/12 14:31
 */
public class PageUtil {

    /**
     * IPage<T> -> PageResponse<T>
     */
    public static <T, E extends IPage<T>> PageResponse<T> buildPageResponse(E e) {
        PageResponse<T> response = new PageResponse<T>();
        response.setPageNo(e.getCurrent());
        response.setPageSize(e.getSize());
        response.setTotal(e.getTotal());
        response.setList(e.getRecords());
        return response;
    }

    /**
     * PageResponse<R> -> PageResponse<T>
     */
    public static <T, R, E extends PageResponse<R>> PageResponse<T> buildPageResponse(E e, Function<R, T> function) {
        PageResponse<T> response = new PageResponse<T>();
        response.setPageNo(e.getPageNo());
        response.setPageSize(e.getPageSize());
        response.setTotal(e.getTotal());
        List<R> records = e.getList();
        if (CollectionUtils.isNotEmpty(records)) {
            List<T> list = records.stream().map(function).collect(Collectors.toList());
            response.setList(list);
        }
        return response;
    }

    /**
     * IPage<R> -> PageResponse<T>
     */
    public static <T, R, E extends IPage<R>> PageResponse<T> buildPageResponse(E e, Function<R, T> function) {
        PageResponse<R> rPageResponse = buildPageResponse(e);
        return buildPageResponse(rPageResponse, function);
    }

}
