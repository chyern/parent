package com.github.chyern.session.interceptor;

import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
public class WebInterceptor implements HandlerInterceptor {

    public static final String TOKEN_NAME = "chyern-token";

    private static ThreadLocal<String> threadLocal;

    @Synchronized
    public static String getSessionId() {
        String sessionId = threadLocal.get();
        if (StringUtils.isBlank(sessionId)) {
            sessionId = UUID.randomUUID().toString();
            threadLocal.set(sessionId);
        }
        return sessionId;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = Arrays.stream(cookies).filter(item -> TOKEN_NAME.equals(item.getName())).findFirst().orElse(null);
        if (cookie != null) {
            threadLocal.set(cookie.getValue());
        } else {
            getSessionId();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String sessionId = getSessionId();
        Cookie cookie = new Cookie(TOKEN_NAME, sessionId);
        cookie.setPath("/");
        response.addCookie(cookie);
        threadLocal.remove();
    }
}
