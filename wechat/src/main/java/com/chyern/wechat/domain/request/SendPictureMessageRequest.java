package com.chyern.wechat.domain.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 17:54
 */
@Data
public class SendPictureMessageRequest extends SendMessageRequest implements Serializable {
    private static final long serialVersionUID = -2080963524113821008L;

    /**
     * 消息类型，此时固定为：image
     */
    @SerializedName("msgtype")
    private String msgtype = "image";
    /**
     * 消息内容，最长不超过2048个字节，超过将截断（支持id转译）
     */
    @SerializedName("image")
    private PictureRequest image;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PictureRequest implements Serializable {
        private static final long serialVersionUID = -1450976958409938488L;
        /**
         * 图片媒体文件id，可以调用上传临时素材接口获取
         */
        @SerializedName("media_id")
        private String mediaId;
    }
}
