package com.github.chyern.permission.aspect;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.utils.AssertUtil;
import com.github.chyern.permission.annotation.Permission;
import com.github.chyern.permission.processor.IPermissionProcessor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/7/2
 */
@Aspect
public class PermissionAspect {

    @Autowired
    private IPermissionProcessor permissionProcessor;

    @Pointcut("@annotation(com.github.chyern.permission.annotation.Permission)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        Permission annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Permission.class);
        String permissionCode = annotation.permissionCode();
        Boolean hasPermission = permissionProcessor.hasPermission(permissionCode);
        AssertUtil.isTrue(hasPermission, ErrorEnum.WITHOUT_PERMISSION);
    }
}
