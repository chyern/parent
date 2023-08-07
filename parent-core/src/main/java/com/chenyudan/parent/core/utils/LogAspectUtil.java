package com.chenyudan.parent.core.utils;

import ch.qos.logback.core.helpers.ThrowableToStringArray;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Description: 切面日志工具类
 *
 * @author Chyern
 * @since 2022/11/24 15:14
 */
@Slf4j
public class LogAspectUtil {

    public static final String TRACK_ID = "trackId";

    public static Object excute(ProceedingJoinPoint point) throws Throwable {
        String trackId = MDC.get(TRACK_ID);

        boolean notExist = StringUtils.isBlank(trackId);
        if (notExist) {
            MDC.put(TRACK_ID, UUID.randomUUID().toString());
        }

        StringBuilder logStr = new StringBuilder(System.lineSeparator());
        long t = System.currentTimeMillis();
        Object proceed;
        try {
            Signature signature = point.getSignature();
            logStr.append("class:").append(signature.getDeclaringTypeName()).append("#").append(signature.getName()).append(System.lineSeparator());

            String argStr = new GsonBuilder().create().toJson(point.getArgs());
            argStr = StringUtil.substring(argStr, 0, 200);
            logStr.append("args:").append(argStr).append(System.lineSeparator());

            proceed = point.proceed();

            logStr.append("consume:").append(System.currentTimeMillis() - t).append(System.lineSeparator());

            String result = new GsonBuilder().create().toJson(proceed);
            result = StringUtil.substring(result, 0, 200);
            logStr.append("result:").append(result);

            log.info(logStr.toString());
            return proceed;
        } catch (Throwable throwable) {
            logStr.append("consume:").append(System.currentTimeMillis() - t).append(System.lineSeparator());
            logStr.append("result:").append(throwable.getMessage());
            log.error(logStr.toString());
            if (notExist) {
                log.error(StringUtils.join(ThrowableToStringArray.convert(throwable), System.lineSeparator()));
            }
            throw throwable;
        } finally {
            if (notExist) {
                MDC.clear();
            }
        }
    }
}
