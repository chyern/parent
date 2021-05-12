package com.github.chyern.permission.interceptor;

import com.github.chyern.permission.annotation.Permission;
import com.github.chyern.permission.processor.PermissionProcessor;
import org.apache.commons.lang3.StringUtils;
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
public class PermissionInterceptor implements HandlerInterceptor {

    @Value("${permission.without}")
    private String returnStr;

    @Resource
    private PermissionProcessor permissionHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
            if (permission != null && StringUtils.isNoneBlank(permission.value())) {
                Boolean hasPermission = permissionHandler.hasPermission(permission.value());
                if (!hasPermission) {
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(returnStr);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
