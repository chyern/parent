package com.chenyudan.parent.api.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/6/15 17:44
 */
public class ContextUtil {

    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static final String LANG = "lang";

    private static final String USER_ID = "user_id";

    private static final String LOGIN_USER = "login_user";

    private static final String ORGANIZATION_ID = "organization_id";

    private static final String TENANT_ID = "tenant_id";

    public static Map<String, Object> getContext() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        return map;
    }

    public static void putContext(String key, Object value) {
        Map<String, Object> map = getContext();
        map.put(key, value);
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static void putLang(String lang) {
        putContext(LANG, lang);
    }

    public static Locale getLang() {
        Object langObj = getContext().get(LANG);
        if (langObj == null || langObj.toString().isEmpty()) {
            return Locale.SIMPLIFIED_CHINESE;
        } else {
            return new Locale(langObj.toString());
        }
    }

    public static void putUserId(String userId) {
        putContext(USER_ID, userId);
    }

    public static String getUserId() {
        Map<String, Object> context = getContext();
        return context.containsKey(USER_ID) ? (String) context.get(USER_ID) : null;
    }

    public static void putLoginUser(Object value) {
        putContext(LOGIN_USER, value);
    }

    public static Object getLoginUser() {
        return getContext().get(LOGIN_USER);
    }

    public static void putOrganizationId(String organizationId) {
        putContext(ORGANIZATION_ID, organizationId);
    }

    public static String getOrganizationId() {
        Map<String, Object> context = getContext();
        return context.containsKey(ORGANIZATION_ID) ? (String) context.get(ORGANIZATION_ID) : null;
    }

    public static void putTenantId(String tenantId) {
        putContext(TENANT_ID, tenantId);
    }

    public static String getTenantId() {
        Map<String, Object> context = getContext();
        return context.containsKey(TENANT_ID) ? (String) context.get(TENANT_ID) : null;
    }


}
