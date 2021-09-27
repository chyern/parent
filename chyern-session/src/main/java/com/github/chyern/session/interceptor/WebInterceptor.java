package com.github.chyern.session.interceptor;

import com.alibaba.fastjson.JSON;
import com.github.chyern.common.exception.Exception;
import com.github.chyern.common.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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
@Component
public class WebInterceptor implements HandlerInterceptor {

    public static final String TOKEN_NAME = "chyern-token";

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getToken() {
        return threadLocal.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //从cookie中获取
        String token = getToken(request.getCookies());
        if (StringUtils.isBlank(token)) {
            //从header中获取
            token = request.getHeader(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {
                //生成token
                token = UUID.randomUUID().toString();
            }
        }
        threadLocal.set(token);
        setResponse(response);
        return true;
    }

    private String getToken(Cookie[] cookies) {
        if (cookies != null) {
            Cookie cookie = Arrays.stream(cookies).filter(item -> TOKEN_NAME.equals(item.getName())).findFirst().orElse(null);
            if (cookie != null) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void setResponse(HttpServletResponse response) {
        String token = getToken();
        Cookie cookie = new Cookie(TOKEN_NAME, token);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.addHeader(TOKEN_NAME, token);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, java.lang.Exception ex) throws java.lang.Exception {
        try {
            if (ex instanceof Exception) {
                Exception exception = (Exception) ex;
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSON.toJSONString(Response.buildFailure(exception.getError())));
            }
        } catch (java.lang.Exception ignore) {
        }
        threadLocal.remove();
    }

}
