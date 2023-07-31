package com.chenyudan.parent.wechat.connect.common;

import com.chenyudan.parent.connect.Connect;
import com.chenyudan.parent.connect.processor.annotation.method.RequestMapping;
import com.chenyudan.parent.connect.processor.annotation.resource.Queries;
import com.chenyudan.parent.connect.processor.constant.Method;
import com.chenyudan.parent.wechat.connect.common.domain.response.AccessTokenResponse;
import com.chenyudan.parent.wechat.connect.common.domain.response.ApiDomainIpResponse;
import com.chenyudan.parent.wechat.connect.common.process.WechatConnectProcessor;

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
    AccessTokenResponse getAccessToken(@Queries("corpid") String corpid, @Queries("corpsecret") String corpsecret);

    /**
     * 获取企业微信API域名IP段
     *
     * @param access_token 调用接口凭证
     * @return
     */
    @RequestMapping(value = "/cgi-bin/get_api_domain_ip", method = Method.GET)
    ApiDomainIpResponse getApiDomainIps(@Queries("access_token") String access_token);
}
