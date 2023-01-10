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

    public static void push(Object object) {
        if (object == null) {
            return;
        }

        String s = new GsonBuilder().create().toJson(object);
        Class aClass = object.getClass();
        AbstractSpringMessageConsumerHandle handleMap = AbstractSpringMessageConsumerHandle.getHandleMap(aClass);
        if (handleMap != null) {
            handleMap.product(object);
        }
    }
}
