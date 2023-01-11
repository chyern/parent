package com.chyern.wechat.utils;


import com.chyern.wechat.content.Content;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 11:31
 */
@Slf4j
public class SendMessageUtil {

    /**
     * corpid应用组织编号   corpsecret应用秘钥
     * 获取toAuth(String Get_Token_Url)返回结果中键值对中access_token键的值
     *
     * @param
     */
    public static String getToken(String corpid, String corpsecret) throws IOException {
        String getTokenUrl = String.format(Content.GET_TOKEN_URL, corpid, corpsecret);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(getTokenUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                String resp = EntityUtils.toString(entity, Content.UTF_8);
                EntityUtils.consume(entity);

                Map<String, Object> map = new GsonBuilder().create().fromJson(resp, new TypeToken<Map<String, Object>>() {
                }.getType());
                return map.get("access_token").toString();
            }
        }
    }


    /**
     * @return String
     * @Title:创建微信发送请求post数据 touser发送消息接收者    ，msgtype消息类型（文本/图片等），
     * application_id应用编号。
     * 本方法适用于text型微信消息，contentKey和contentValue只能组一对
     */
    public static String createpostdata(String touser, String msgtype, int application_id, String contentKey, String contentValue) {
        WeChatData wcd = new WeChatData();
        wcd.setTouser(touser);
        wcd.setAgentid(application_id);
        wcd.setMsgtype(msgtype);
        Map<Object, Object> content = new HashMap<Object, Object>();
        content.put(contentKey, contentValue);
        wcd.setText(content);
        return new GsonBuilder().create().toJson(wcd);
    }


    /**
     * @return String
     * @Title:创建微信发送请求post数据 touser发送消息接收者    ，msgtype消息类型（文本/图片等），
     * application_id应用编号。
     * 本方法适用于text型微信消息，contentKey和contentValue只能组一对
     */
    public static String createpostdataForParty(String toparty, String msgtype, int application_id, String contentKey, String contentValue) {
        WeChatData wcd = new WeChatData();
        wcd.setToparty(toparty);
        wcd.setAgentid(application_id);
        wcd.setMsgtype(msgtype);
        Map<Object, Object> content = new HashMap<Object, Object>();
        content.put(contentKey, contentValue);
        wcd.setText(content);
        return new GsonBuilder().create().toJson(wcd);
    }

    /**
     * @return String
     * @Title 创建微信发送请求post实体
     * charset消息编码    ，contentType消息体内容类型，
     * url微信消息发送请求地址，data为post数据，token鉴权token
     */
    public static String post(String data, String token) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(Content.SEND_MESSAGE_URL + token);
            httpPost.setHeader(Content.CONTENT_TYPE, Content.CONTENT_TYPE);
            httpPost.setEntity(new StringEntity(data, Content.UTF_8));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                String resp = EntityUtils.toString(entity, Content.UTF_8);
                EntityUtils.consume(entity);
                return resp;
            }
        }
    }

    @Data
    private static class WeChatData {
        /**
         * 成员账号
         */
        private String touser;
        /**
         * 部门账号
         */
        private String toparty;
        /**
         * 消息类型
         */
        private String msgtype;
        /**
         * 企业应用的agentID
         */
        private int agentid;
        /**
         * 实际接收Map类型数据
         */
        private Object text;
    }
}
