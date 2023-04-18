package com.chyern.parent.mysql.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * Description: 切换数据源拦截器
 *
 * @author Chyern
 * @since 2023/4/18 14:20
 */
@Aspect
@Slf4j
public class DataSourceSwitchAspect {

    @Pointcut(value = "@annotation(com.chyern.parent.mysql.datasource.DataSourceSwitch)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            Signature signature = point.getSignature();
            DataSourceSwitch dataSourceSwitch = AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSourceSwitch.class);
            return point.proceed();
        } finally {
            DataSourceContextHolder.remove();
        }
    }
}
