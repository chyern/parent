package com.chenyudan.parent.core.utils;


import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * 翻译工具类
 */
public class TranslationUtil {

    private static MessageSource messageSource;

    /**
     * 获取国际化文本
     */
    public static String getMessage(String code, Locale locale) {
        return getMessage(code, null, locale);
    }


    /**
     * 获取国际化文本
     */
    public static String getMessage(String code, String defaultMessage, Locale locale) {
        return getMessage(code, defaultMessage, locale, (Object) null);
    }


    /**
     * 获取国际化文本
     */
    public static String getMessage(String code, Locale locale, Object... params) {
        return getMessage(code, null, locale, params);
    }


    /**
     * 获取国际化文本
     */
    public static String getMessage(String code, String defaultMessage, Locale locale, Object... params) {
        if (messageSource == null) {
            messageSource = SpringContextUtil.getBean(MessageSource.class);
        }
        return messageSource.getMessage(code, params, defaultMessage, locale);
    }
}
