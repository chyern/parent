package com.chyern.permission.aspect;

import com.chyern.core.utils.AssertUtil;
import com.chyern.permission.annotation.Permission;
import com.chyern.permission.exception.PermissionErrorEnum;
import com.chyern.permission.processor.IPermissionProcessor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;


/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/2
 */
@Aspect
public class PermissionAspect {

    @Resource
    private IPermissionProcessor permissionProcessor;

    @Pointcut("@annotation(com.chyern.permission.annotation.Permission)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        Permission annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Permission.class);
        String permissionCode = annotation.permissionCode();
        Boolean hasPermission = permissionProcessor.hasPermission(permissionCode);
        AssertUtil.isTrue(hasPermission, PermissionErrorEnum.WITHOUT_PERMISSION);
    }
}
