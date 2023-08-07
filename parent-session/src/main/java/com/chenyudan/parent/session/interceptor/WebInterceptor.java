package com.chenyudan.parent.session.interceptor;

import com.chenyudan.parent.core.utils.LambdaUtil;
import com.chenyudan.parent.core.utils.StringUtil;
import com.chenyudan.parent.session.config.SessionConfigProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
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

    @Resource
    private SessionConfigProperties sessionConfigProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //从cookie中获取
        String token = getToken(request.getCookies());
        if (StringUtil.isBlank(token)) {
            //从header中获取
            token = request.getHeader(sessionConfigProperties.getTokenKey());
            if (StringUtil.isBlank(token)) {
                //生成token
                token = UUID.randomUUID().toString();
            }
        }
        TokenThreadLocal.set(token);
        setResponse(response);
        return true;
    }

    private String getToken(Cookie[] cookies) {
        if (cookies != null) {
            Cookie cookie = LambdaUtil.findFirst(Arrays.asList(cookies), item -> sessionConfigProperties.getTokenKey().equals(item.getName()));
            if (cookie != null) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void setResponse(HttpServletResponse response) {
        String token = TokenThreadLocal.get();
        Cookie cookie = new Cookie(sessionConfigProperties.getTokenKey(), token);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.addHeader(sessionConfigProperties.getTokenKey(), token);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenThreadLocal.remove();
    }


    public static class TokenThreadLocal {

        private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

        public static String get() {
            return threadLocal.get();
        }

        public static void set(String token) {
            threadLocal.set(token);
        }

        public static void remove() {
            threadLocal.remove();
        }
    }

}
