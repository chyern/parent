package com.github.chyen.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
public class SessionHandlerInterceptor implements HandlerInterceptor {

    @Value("${session.without.login}")
    private String returnStr;

    @Resource
    private SessionManagement sessionManagement;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Object session = sessionManagement.getSession();
        if (session != null) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(returnStr);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.hasMethodAnnotation(LoginOut.class)) {
            sessionManagement.removeSession();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
