package com.chyern.message.utils;

import com.chyern.message.spring.AbstractSpringMessageConsumerHandle;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/10 15:21
 */
@Slf4j
public class MessageUtil {

    public static void pushSpringMessage(Object object) {
        if (object == null) {
            return;
        }

        log.info("发送消息spring消息:{}", new GsonBuilder().create().toJson(object));
        Class aClass = object.getClass();
        AbstractSpringMessageConsumerHandle handleMap = AbstractSpringMessageConsumerHandle.getHandleMap(aClass);
        if (handleMap != null) {
            handleMap.product(object);
        }
    }
}
