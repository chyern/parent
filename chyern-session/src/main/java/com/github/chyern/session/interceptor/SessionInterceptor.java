package com.github.chyern.session.interceptor;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.utils.AssertUtil;
import com.github.chyern.session.annotation.LoginOut;
import com.github.chyern.session.processor.SessionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    private SessionManagement sessionManagement;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object session = sessionManagement.getSession();
        AssertUtil.isNull(session, ErrorEnum.WITHOUT_LOGIN);
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
