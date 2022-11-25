package com.chyern.core.utils;

import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/11/24 15:14
 */
@Slf4j
public class LogAspectUtil {

    private static final String TRACK_ID = "trackId";

    public static Object excute(ProceedingJoinPoint point) throws Throwable {
        String trackId = MDC.get(TRACK_ID);
        if (StringUtils.isBlank(trackId)) {
            MDC.put("trackId", UUID.randomUUID().toString());
        }
        StringBuilder logStr = new StringBuilder(System.lineSeparator());
        long t = System.currentTimeMillis();
        Object proceed;
        try {
            Signature signature = point.getSignature();
            logStr.append("class:").append(signature.getDeclaringTypeName()).append("#").append(signature.getName()).append(System.lineSeparator());
            String argStr = new GsonBuilder().create().toJson(point.getArgs());
            logStr.append("args:").append(argStr).append(System.lineSeparator());
            proceed = point.proceed();
            logStr.append("consume:").append(System.currentTimeMillis() - t).append(System.lineSeparator());
            logStr.append("result:").append(new GsonBuilder().create().toJson(proceed));
            log.info(logStr.toString());
            return proceed;
        } catch (Throwable throwable) {
            logStr.append("consume:").append(System.currentTimeMillis() - t).append(System.lineSeparator());
            logStr.append("result:").append(throwable.getMessage());
            log.error(logStr.toString());
            throw throwable;
        } finally {
            MDC.clear();
        }
    }
}