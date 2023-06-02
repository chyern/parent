package com.chenyudan.parent.wechat.connect.message.domain;

import com.chenyudan.parent.connect.processor.connect.Connect;
import com.chenyudan.parent.connect.processor.connect.annotation.method.RequestMapping;
import com.chenyudan.parent.connect.processor.connect.annotation.resource.Body;
import com.chenyudan.parent.connect.processor.connect.annotation.resource.Query;
import com.chenyudan.parent.connect.processor.connect.constant.Method;
import com.chenyudan.parent.wechat.connect.message.domain.request.SendMessageRequest;
import com.chenyudan.parent.wechat.connect.message.domain.response.SendMessageResponse;

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
