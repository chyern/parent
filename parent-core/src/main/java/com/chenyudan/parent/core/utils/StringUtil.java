package com.chenyudan.parent.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/6/6 14:44
 */
public class StringUtil extends StringUtils {

    private static final String[] EMPTY_STRING_ARRAY = {};

    /**
     * Copy the given {@link Collection} into a {@code String} array.
     * <p>The {@code Collection} must contain {@code String} elements only.
     * @param collection the {@code Collection} to copy
     * (potentially {@code null} or empty)
     * @return the resulting {@code String} array
     */
    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return (!CollectionUtils.isEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY);
    }
}
