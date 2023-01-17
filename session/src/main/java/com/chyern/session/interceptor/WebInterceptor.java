package com.chyern.session.interceptor;

import com.chyern.core.utils.LambdaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    @Value("${session.token-key}")
    private String TOKEN_KEY;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //从cookie中获取
        String token = getToken(request.getCookies());
        if (StringUtils.isBlank(token)) {
            //从header中获取
            token = request.getHeader(TOKEN_KEY);
            if (StringUtils.isBlank(token)) {
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
            Cookie cookie = LambdaUtil.findFirst(Arrays.asList(cookies), item -> TOKEN_KEY.equals(item.getName()));
            if (cookie != null) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void setResponse(HttpServletResponse response) {
        String token = TokenThreadLocal.get();
        Cookie cookie = new Cookie(TOKEN_KEY, token);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.addHeader(TOKEN_KEY, token);
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

    // byte转为char
    private static char[] byteToChar(byte[] bytes) {
        Charset charset = StandardCharsets.ISO_8859_1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        CharBuffer charBuffer = charset.decode(byteBuffer);
        return charBuffer.array();
    }

}
