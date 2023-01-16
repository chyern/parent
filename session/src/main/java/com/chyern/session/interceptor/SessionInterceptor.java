package com.chyern.session.interceptor;

import com.chyern.session.annotation.LoginOut;
import com.chyern.session.processor.SessionManagement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Value("${session.call-back-url}")
    private String CALL_BACK_URL;

    @Resource
    private SessionManagement sessionManagement;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object session = sessionManagement.getSession();
        if (session != null) {
            return true;
        } else {
            response.sendRedirect(CALL_BACK_URL);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(LoginOut.class)) {
                sessionManagement.removeSession();
            }
        }
    }
}
