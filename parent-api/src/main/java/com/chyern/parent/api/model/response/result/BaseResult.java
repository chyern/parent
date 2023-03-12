package com.chyern.parent.api.model.response.result;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/2 14:41
 */
@Data
public class BaseResult implements Serializable {
    private static final long serialVersionUID = 5604336341064298376L;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 租户ID
     */
    private String tenantId;

}
