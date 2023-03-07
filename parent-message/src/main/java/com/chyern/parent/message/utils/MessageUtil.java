package com.chyern.parent.message.utils;

import com.chyern.parent.message.spring.AbstractSpringMessageConsumerHandle;
import com.chyern.parent.message.spring.domain.SpringMessage;
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

    public static <T extends SpringMessage> void pushSpringMessage(T t) {
        if (t == null) {
            return;
        }

        log.info("发送消息spring消息:{}", new GsonBuilder().create().toJson(t));
        Class aClass = t.getClass();
        AbstractSpringMessageConsumerHandle<T> handleMap = AbstractSpringMessageConsumerHandle.getHandleMap(aClass);
        if (handleMap != null) {
            handleMap.product(t);
        }
    }
}
