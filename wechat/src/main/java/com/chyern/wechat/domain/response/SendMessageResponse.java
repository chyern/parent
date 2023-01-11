package com.chyern.wechat.domain.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 17:35
 */
@Data
public class SendMessageResponse implements Serializable {
    private static final long serialVersionUID = 8401553052986450728L;

    /**
     * 返回码
     */
    @SerializedName("errcode")
    private Integer errcode;
    /**
     * 对返回码的文本描述内容
     */
    @SerializedName("errmsg")
    private String errmsg;
    /**
     * 不合法的userid，不区分大小写，统一转为小写
     */
    @SerializedName("invaliduser")
    private String invaliduser;
    /**
     * 不合法的partyid
     */
    @SerializedName("invalidparty")
    private String invalidparty;
    /**
     * 不合法的标签id
     */
    @SerializedName("invalidtag")
    private String invalidtag;
    /**
     * 没有基础接口许可(包含已过期)的userid
     */
    @SerializedName("unlicenseduser")
    private String unlicenseduser;
    /**
     * 消息id，用于撤回应用消息
     */
    @SerializedName("msgid")
    private String msgid;
    /**
     * 仅消息类型为“按钮交互型”，“投票选择型”和“多项选择型”的模板卡片消息返回，应用可使用response_code调用更新模版卡片消息接口，72小时内有效，且只能使用一次
     */
    @SerializedName("response_code")
    private String responseCode;
}
