package com.chenyudan.parent.wechat.connect.message.domain.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/1/11 17:41
 */
@Data
public class SendMessageRequest implements Serializable {

    private static final long serialVersionUID = 3041718657500498379L;

    /**
     * 指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
     * 特殊情况：指定为"@all"，则向该企业应用的全部成员发送
     */
    @SerializedName("touser")
    private String touser;
    /**
     * 指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
     * 当touser为"@all"时忽略本参数
     */
    @SerializedName("toparty")
    private String toparty;
    /**
     * 指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
     * 当touser为"@all"时忽略本参数
     */
    @SerializedName("totag")
    private String totag;
    /**
     * 企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
     */
    @SerializedName("agentid")
    private Integer agentid;
    /**
     * 表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
     */
    @SerializedName("safe")
    private Integer safe;
    /**
     * 表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略。
     */
    @SerializedName("enable_id_trans")
    private Integer enableIdTrans;
    /**
     * 表示是否开启重复消息检查，0表示否，1表示是，默认0
     */
    @SerializedName("enable_duplicate_check")
    private Integer enableDuplicateCheck;
    /**
     * 表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
     */
    @SerializedName("duplicate_check_interval")
    private Integer duplicateCheckInterval;


}
