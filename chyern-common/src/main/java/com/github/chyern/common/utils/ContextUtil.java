package com.github.chyern.common.utils;

import com.github.chyern.common.model.Context;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/9/27
 */
public class ContextUtil {

    private static ThreadLocal<Context> ctx = new ThreadLocal<>();

    public static Context get() {
        return ctx.get();
    }

    public static void set(Context context) {
        ctx.set(context == null ? new Context() : context);
    }

    public static void remove() {
        ctx.remove();
    }
}
