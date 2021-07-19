package com.github.chyern.permission.aspect;

import com.github.chyern.common.enums.ChyernErrorEnum;
import com.github.chyern.common.exception.ChyernException;
import com.github.chyern.permission.annotation.Permission;
import com.github.chyern.permission.processor.PermissionProcessor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/2
 */
@Aspect
@Component
public class PermissionAspect {

    @Resource
    private PermissionProcessor permissionProcessor;

    @Pointcut("@annotation(com.github.chyern.permission.annotation.Permission)")
    public void cut() {
    }

    @Before("cut()")
    public void around(JoinPoint joinPoint) {
        Permission annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Permission.class);
        String permissionCode = annotation.permissionCode();
        if (StringUtils.isNoneBlank(permissionCode)) {
            Boolean hasPermission = permissionProcessor.hasPermission(permissionCode);
            if (!hasPermission) {
                throw new ChyernException(ChyernErrorEnum.WITHOUT_PERMISSION);
            }
        }
    }
}
