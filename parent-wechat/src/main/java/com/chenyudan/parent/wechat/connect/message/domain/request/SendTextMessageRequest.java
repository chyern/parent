package com.chenyudan.parent.wechat.connect.message.domain.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 17:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SendTextMessageRequest extends SendMessageRequest {

    private static final long serialVersionUID = 143443776392867073L;

    /**
     * 消息类型，此时固定为：text
     */
    @SerializedName("msgtype")
    private String msgtype = "text";
    /**
     * 消息内容，最长不超过2048个字节，超过将截断（支持id转译）
     */
    @SerializedName("text")
    private TextRequest text;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TextRequest implements Serializable {
        private static final long serialVersionUID = -1450976958409938488L;
        /**
         * 消息内容，最长不超过2048个字节，超过将截断（支持id转译）
         */
        @SerializedName("content")
        private String content;
    }
}
