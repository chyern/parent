package com.chyern.parent.api.model.request.command;

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

    /**
     * 页数
     */
    private Long pageNo = 1L;

    /**
     * 页大小
     */
    private Long pageSize = 10L;
}
