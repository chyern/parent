package com.chyern.parent.api.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/3/2 15:13
 */
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = -5987707930370268639L;

    /**
     * 请求ID
     */
    private String requestId = UUID.randomUUID().toString();

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 语言
     */
    private String lang = Locale.CHINESE.toString();

    /**
     * 租户ID
     */
    private String tenantId;
}
