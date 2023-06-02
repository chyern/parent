package com.chenyudan.parent.session.interceptor;

import com.chenyudan.parent.session.annotation.LoginOut;
import com.chenyudan.parent.session.processor.IWithoutLoginProcessor;
import com.chenyudan.parent.session.processor.SessionManagement;
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

    @Resource
    private SessionManagement sessionManagement;

    @Resource
    private IWithoutLoginProcessor withoutLoginProcessor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object session = sessionManagement.getSession();
        if (session != null) {
            return true;
        } else {
            withoutLoginProcessor.processor(response);
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
