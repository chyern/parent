package com.chyern.spicore.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/1 16:09
 */
@Data
public class BaseCommand implements Serializable {
    private static final long serialVersionUID = -5945025824802201338L;

    /**
     * 组织Id
     */
    private String organizationId;

    /**
     * 语言
     */
    private String lang;

    /**
     * 用户
     */
    private String userCode;

    /**
     * 请求id
     */
    private String requestId = UUID.randomUUID().toString();

    /**
     * 请求ip
     */
    private String requestIp;
}
