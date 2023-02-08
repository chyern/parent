package com.chyern.mysql.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chyern.mysql.domain.PageResponse;
import org.springframework.util.CollectionUtils;

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

    public static <T, E extends IPage<R>, R> PageResponse<T> buildPageResponse(E e, Function<R, T> function) {
        PageResponse<T> response = new PageResponse<T>();
        response.setPageNo(e.getCurrent());
        response.setPageSize(e.getSize());
        response.setTotal(e.getTotal());
        List<R> records = e.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            List<T> list = records.stream().map(function).collect(Collectors.toList());
            response.setList(list);
        }
        return response;
    }

    public static <T, E extends PageResponse<R>, R> PageResponse<T> buildPageResponse(E e, Function<R, T> function) {
        PageResponse<T> response = new PageResponse<T>();
        response.setPageNo(e.getPageNo());
        response.setPageSize(e.getPageSize());
        response.setTotal(e.getTotal());
        List<R> records = e.getList();
        if (!CollectionUtils.isEmpty(records)) {
            List<T> list = records.stream().map(function).collect(Collectors.toList());
            response.setList(list);
        }
        return response;
    }

}
