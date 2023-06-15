package com.chenyudan.parent.database.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chenyudan.parent.core.convert.ConvertFunctionalInterface;
import com.chenyudan.parent.database.mybatis.domain.PageRequest;
import com.chenyudan.parent.database.mybatis.domain.PageResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/12/12 14:31
 */
public class PageUtil {

    public static <T, R> PageRequest<R> buildPageRequest(PageRequest<T> pageRequest, ConvertFunctionalInterface<T, R> functionalInterface) {
        PageRequest<R> request = new PageRequest<>();
        pageRequest.setPageNo(request.getPageNo());
        pageRequest.setPageSize(request.getPageSize());
        R convert = functionalInterface.convert(pageRequest.getData());
        request.setData(convert);
        return request;
    }


    /**
     * IPage<T> -> PageResponse<T>
     */
    public static <T> PageResponse<T> buildPageResponse(IPage<T> iPage) {
        PageResponse<T> response = new PageResponse<T>();
        response.setPageNo(iPage.getCurrent());
        response.setPageSize(iPage.getSize());
        response.setTotal(iPage.getTotal());
        response.setList(iPage.getRecords());
        return response;
    }

    /**
     * PageResponse<T> -> PageResponse<R>
     */
    public static <T, R> PageResponse<R> buildPageResponse(PageResponse<T> pageResponse, ConvertFunctionalInterface<T, R> functionalInterface) {
        PageResponse<R> response = new PageResponse<>();
        response.setPageNo(pageResponse.getPageNo());
        response.setPageSize(pageResponse.getPageSize());
        response.setTotal(pageResponse.getTotal());
        List<T> records = pageResponse.getList();
        if (CollectionUtils.isNotEmpty(records)) {
            List<R> list = records.stream().map(functionalInterface::convert).collect(Collectors.toList());
            response.setList(list);
        }
        return response;
    }

    /**
     * IPage<T> -> PageResponse<R>
     */
    public static <T, R> PageResponse<R> buildPageResponse(IPage<T> iPage, ConvertFunctionalInterface<T, R> functionalInterface) {
        PageResponse<T> pageResponse = buildPageResponse(iPage);
        return buildPageResponse(pageResponse, functionalInterface);
    }

}
