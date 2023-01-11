package com.chyern.wechat;

import com.chyern.wechat.utils.SendMessageUtil;

import java.io.IOException;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since ${DATE} ${TIME}
 */
public class Main {

    //修改WeChatUrlData文件main方法里参数，替换为自己企业的。这里的XXX1是上面的企业ID,XXX2是Secret,XXX3是AgentId,XXX4是部门id

    public static void main(String[] args) {
        try {
            String token = SendMessageUtil.getToken("wwfc5d935dfc6a4526", "CVnS2z3OYrPyQbIPDgOBQ76LnfhCpI_nZa40Ox9c_D0");
            System.out.println("获取到的token======>" + token);

            //给所有人发
            String postdata = SendMessageUtil.createpostdata("@all", "text", 1000002, "content", "开始巡检！");
            //给单个人发
//            String postdata = SendMessageUtil.createpostdata( "LiYuanFang", "text", XXX3, "content", "开始巡检！");
            //给好几个人发，用|分隔
//            String postdata = SendMessageUtil.createpostdata( "LiYuanFang|lying", "text", XXX3, "content", "开始巡检！");
            //给部门发
//            String postdata = SendMessageUtil.createpostdataForParty("XXX4", "text", XXX3, "content", "开始巡检！");
            System.out.println("请求数据======>" + postdata);

            String resp = SendMessageUtil.post(postdata, token);
            System.out.println("发送微信的响应数据======>" + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}