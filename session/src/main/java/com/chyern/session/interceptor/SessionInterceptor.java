package com.chyern.session.interceptor;

import com.chyern.core.utils.AssertUtil;
import com.chyern.session.annotation.LoginOut;
import com.chyern.session.exception.SessionErrorEnum;
import com.chyern.session.processor.SessionManagement;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object session = sessionManagement.getSession();
        AssertUtil.isNull(session, SessionErrorEnum.WITHOUT_LOGIN);
        return true;
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
