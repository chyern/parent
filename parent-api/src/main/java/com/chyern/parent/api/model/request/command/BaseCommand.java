package com.chyern.parent.api.model.request.command;

import com.chyern.parent.api.model.request.Request;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/1 16:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCommand extends Request {

    /**
     * 用户
     */
    private String userId;

}
