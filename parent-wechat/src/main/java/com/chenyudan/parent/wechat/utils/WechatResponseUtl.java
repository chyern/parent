package com.chenyudan.parent.wechat.utils;

import com.chenyudan.parent.api.exception.BaseException;
import com.chenyudan.parent.wechat.connect.common.domain.response.BaseResponse;

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
        if (errcode != null && errcode != 0) {
            throw new BaseException(errcode, errmsg);
        }
    }
}
