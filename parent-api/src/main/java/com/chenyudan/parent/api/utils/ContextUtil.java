package com.chenyudan.parent.api.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/6/15 17:44
 */
public class ContextUtil {

    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static final String LANG = "lang";

    private static final String USER_ID = "user_id";

    private static final String LOGIN_USER = "login_user";

    public static Map<String, Object> get() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map;
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static void put(String key, Object value) {
        Map<String, Object> map = get();
        map.put(key, value);
    }

    public static void putLang(String lang) {
        Map<String, Object> map = get();
        map.put(LANG, lang);
    }

    public static Locale getLang() {
        Map<String, Object> map = get();
        String value = (String) map.get(LANG);
        if (value == null || value.trim().length() == 0) {
            return Locale.SIMPLIFIED_CHINESE;
        } else {
            return new Locale(value);
        }
    }

    public static void putUserId(String userId) {
        Map<String, Object> map = get();
        map.put(USER_ID, userId);
    }

    public static String getUserId() {
        Map<String, Object> map = get();
        return map.containsKey(USER_ID) ? (String) map.get(USER_ID) : null;
    }

    public static void putLoginUser(Object value) {
        Map<String, Object> map = get();
        map.put(LOGIN_USER, value);
    }

    public static Object getLoginUer() {
        Map<String, Object> map = get();
        return map.get(LOGIN_USER);
    }

}
