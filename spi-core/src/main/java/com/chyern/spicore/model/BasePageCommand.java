package com.chyern.spicore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/1 16:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePageCommand extends BaseCommand {

    private static final long serialVersionUID = 1102714325131353521L;

    /**
     * 页数
     */
    private Long pageNo = 1L;

    /**
     * 页大小
     */
    private Long pageSize = 10L;
}
