package com.github.chyern.session.interceptor;

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

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

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
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie cookie = Arrays.stream(cookies).filter(item -> TOKEN_NAME.equals(item.getName())).findFirst().orElse(null);
            if (cookie != null) {
                sessionId = cookie.getValue();
                threadLocal.set(sessionId);
            }
        }
        if (StringUtils.isBlank(sessionId)) {
            sessionId = getSessionId();
        }
        setResponse(response);
        return true;
    }

    private void setResponse(HttpServletResponse response) {
        String sessionId = getSessionId();
        Cookie cookie = new Cookie(TOKEN_NAME, sessionId);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.addHeader(TOKEN_NAME, sessionId);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        threadLocal.remove();
    }
}
