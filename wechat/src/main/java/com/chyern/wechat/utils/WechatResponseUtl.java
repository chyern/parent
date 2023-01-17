package com.chyern.wechat.utils;

import com.chyern.spicore.exception.BaseException;
import com.chyern.wechat.connect.common.domain.response.BaseResponse;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/17 18:59
 */
public class WechatResponseUtl {

    public static <T extends BaseResponse> void check(T response) {
        Integer errcode = response.getErrcode();
        String errmsg = response.getErrmsg();
        if (errmsg != null) {
            throw new BaseException(errcode, errmsg);
        }
    }
}
