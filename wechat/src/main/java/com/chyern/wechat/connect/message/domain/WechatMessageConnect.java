package com.chyern.wechat.connect.message.domain;

import com.chyern.connect.annotation.Connect;
import com.chyern.connect.annotation.method.Method;
import com.chyern.connect.annotation.method.RequestMapping;
import com.chyern.connect.annotation.resource.Body;
import com.chyern.connect.annotation.resource.Query;
import com.chyern.wechat.connect.message.domain.request.SendMessageRequest;
import com.chyern.wechat.connect.message.domain.response.SendMessageResponse;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:19
 */
@Connect(value = "https://qyapi.weixin.qq.com")
public interface WechatMessageConnect {

    /**
     * 发送消息
     *
     * @param access_token
     * @param request
     * @return
     */
    @RequestMapping(value = "/cgi-bin/message/send", method = Method.POST)
    <T extends SendMessageRequest> SendMessageResponse sendMessage(@Query("access_token") String access_token, @Body T request);
}
