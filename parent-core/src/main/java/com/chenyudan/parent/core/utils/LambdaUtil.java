package com.chenyudan.parent.core.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Description: lambda工具类
 *
 * @author Chyern
 * @since 2022/3/27 21:29
 */
public class LambdaUtil {

    public static <T, R> List<R> mapToList(List<T> list, Function<? super T, ? extends R> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    public static <R> List<R> mapToList(Map<?,?> map, Function<? super Entry<?,?>, ? extends R> mapper) {
        return map.entrySet().stream().map(mapper).collect(Collectors.toList());
    }

    public static <T> T findFirst(List<T> list, Predicate<? super T> predicate) {
        return list.stream().filter(predicate).findFirst().orElse(null);
    }

    public static <T> boolean anyMatch(List<T> list, Predicate<? super T> predicate) {
        return list.stream().anyMatch(predicate);
    }

    public static <T> boolean allMatch(List<T> list, Predicate<? super T> predicate) {
        return list.stream().allMatch(predicate);
    }

    public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}
