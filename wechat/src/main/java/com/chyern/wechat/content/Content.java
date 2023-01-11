package com.chyern.wechat.content;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 12:08
 */
public interface Content {

    String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    String UTF_8 = "utf-8";

    String CONTENT_TYPE = "Content-Type";
}
