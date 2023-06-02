package com.chenyudan.parent.session.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/3/6 10:59
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "session")
public class SessionConfigProperties {

    /**
     * token标识
     */
    private String tokenKey;

    /**
     * 过滤拦截的路径
     */
    private List<String> excludePathPatterns = new ArrayList<>();
}
