package com.chyern.message.spring.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/10 17:29
 */
@Data
public class SpringMessage implements Serializable {
    private static final long serialVersionUID = -7834523415736280989L;

    private Boolean sync = true;

    private Boolean translation = false;

    private Boolean afterCommit = false;
}
