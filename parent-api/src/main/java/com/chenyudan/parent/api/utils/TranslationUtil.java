package com.chenyudan.parent.api.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import java.util.Locale;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/6/15 17:34
 */
public class TranslationUtil implements MessageSourceAware {

    private static MessageSource messageSource;

    public static String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, null, ContextUtil.getLang());
    }

    public static String getMessage(String code, String defaultMessage, Object... args) {
        return messageSource.getMessage(code, args, defaultMessage, ContextUtil.getLang());
    }

    public static String getMessage(Locale locale, String code, Object... args) {
        return messageSource.getMessage(code, args, null, locale);
    }

    public static String getMessage(Locale locale, String code, String defaultMessage, Object... args) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        TranslationUtil.messageSource = messageSource;
    }
}
