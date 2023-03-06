package com.chyern.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 对象转换工具类,beanutil实现
 *
 * @author Chyern
 * @since 2021/4/20
 */
@Slf4j
public class BeanConvertUtil {

    public static <T> T convert(Object org, Class<T> clazz) {
        T dest = null;
        try {
            dest = clazz.newInstance();
            BeanUtils.copyProperties(org, dest);
        } catch (Exception e) {
            log.error(org.getClass().getName() + "to class " + clazz.getName());
        }
        return dest;
    }

    public static <T> List<T> convert(List<?> org, Class<T> clazz) {
        List<T> dest = new ArrayList<T>();
        org.forEach(item -> dest.add(convert(item, clazz)));
        return dest;
    }
}
