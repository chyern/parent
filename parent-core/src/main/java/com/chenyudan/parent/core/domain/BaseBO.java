package com.chenyudan.parent.core.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Description: 基础DTO
 *
 * @author chenyu
 * @since 2022/12/13 14:17
 */
@Data
public class BaseBO implements Serializable {

    private static final long serialVersionUID = 8028322322166124941L;

    /**
     * 组织ID
     */
    @NotBlank
    private String organizationId;
}
