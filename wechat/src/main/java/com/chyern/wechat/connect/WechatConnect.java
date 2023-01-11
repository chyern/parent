package com.chyern.wechat.connect;

import com.chyern.connect.annotation.Connect;
import com.chyern.connect.annotation.method.Method;
import com.chyern.connect.annotation.method.RequestMapping;
import com.chyern.connect.annotation.resource.Body;
import com.chyern.connect.annotation.resource.Query;
import com.chyern.wechat.connect.process.WechatConnectProcessor;
import com.chyern.wechat.domain.request.SendMessageRequest;
import com.chyern.wechat.domain.response.AccessTokenResponse;
import com.chyern.wechat.domain.response.SendMessageResponse;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:19
 */
@Connect(value = "https://qyapi.weixin.qq.com", processor = WechatConnectProcessor.class)
public interface WechatConnect {

    /**
     * 获取access_token
     *
     * @param corpid     企业ID，获取方式参考：https://developer.work.weixin.qq.com/document/path/91039#14953/corpid
     * @param corpsecret 应用的凭证密钥，注意应用需要是启用状态，获取方式参考：https://developer.work.weixin.qq.com/document/path/91039#14953/secret
     * @return
     */
    @RequestMapping(value = "/cgi-bin/gettoken", method = Method.GET)
    AccessTokenResponse getAccessToken(@Query("corpid") String corpid, @Query("corpsecret") String corpsecret);

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
