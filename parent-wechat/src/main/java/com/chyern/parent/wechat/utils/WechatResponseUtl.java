package com.chyern.parent.wechat.utils;

import com.chyern.parent.api.exception.BaseException;
import com.chyern.parent.wechat.connect.common.domain.response.BaseResponse;

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
