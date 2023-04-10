package com.chyern.parent.wechat.connect.common.process;

import com.chyern.parent.connect.processor.connect.AbstractConnectProcessor;
import com.chyern.parent.wechat.connect.common.domain.response.AccessTokenResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
@Component
public class WechatConnectProcessor extends AbstractConnectProcessor {

    private static final Cache<String, AccessTokenResponse> cache = Caffeine.newBuilder().expireAfterAccess(7200, TimeUnit.SECONDS).build();

    @Override
    public Object execute(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        if ("getAccessToken".equals(method.getName())) {
            String key = StringUtils.join(args, "-");
            result = cache.get(key, func -> {
                try {
                    return (AccessTokenResponse) super.execute(proxy, method, args);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            result = super.execute(proxy, method, args);
        }
        return result;
    }
}
