package com.github.chyern.session.interceptor;

import com.github.chyern.common.enums.ChyernErrorEnum;
import com.github.chyern.common.exception.ChyernException;
import com.github.chyern.session.annotation.LoginOut;
import com.github.chyern.session.processor.SessionManagement;
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
public class SessionInterceptor implements HandlerInterceptor {

    @Resource
    private SessionManagement sessionManagement;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object session = sessionManagement.getSession();
        if (session != null) {
            return true;
        }
        throw new ChyernException(ChyernErrorEnum.WITHOUT_LOGIN);
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
