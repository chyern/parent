package com.chyern.wechat.connect;

import com.chyern.connect.annotation.Connect;
import com.chyern.connect.annotation.method.Method;
import com.chyern.connect.annotation.method.RequestMapping;
import com.chyern.connect.annotation.resource.Query;
import com.chyern.wechat.connect.process.WechatConnectProcessor;
import com.chyern.wechat.domain.AccessTokenResponse;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:19
 */
@Connect(value = "https://qyapi.weixin.qq.com", processor = WechatConnectProcessor.class)
public interface WechatConnect {

    @RequestMapping(value = "/cgi-bin/gettoken", method = Method.GET)
    AccessTokenResponse getAccessToken(@Query("corpid") String corpid, @Query("corpsecret") String corpsecret);
}
