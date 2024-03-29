package com.chenyudan.parent.wechat.connect.message.domain;

import com.chenyudan.parent.connect.Connect;
import com.chenyudan.parent.connect.processor.annotation.method.RequestMapping;
import com.chenyudan.parent.connect.processor.annotation.resource.Body;
import com.chenyudan.parent.connect.processor.annotation.resource.Queries;
import com.chenyudan.parent.connect.processor.constant.Method;
import com.chenyudan.parent.wechat.connect.message.domain.request.SendMessageRequest;
import com.chenyudan.parent.wechat.connect.message.domain.response.SendMessageResponse;

/**
 * Description: TODO
 *
 * @author chenyu
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
    <T extends SendMessageRequest> SendMessageResponse sendMessage(@Queries("access_token") String access_token, @Body T request);
}
