package com.chenyudan.parent.database.mybatis.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/12/6 14:22
 */
public class WrapperUtil {

    @SneakyThrows
    public static <T> QueryWrapper<T> buildQueryWrapper(T t) {
        return new QueryWrapper<T>().setEntity(t);
    }
}
