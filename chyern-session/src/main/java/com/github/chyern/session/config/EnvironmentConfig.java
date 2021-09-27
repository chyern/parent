package com.github.chyern.session.config;

import lombok.Getter;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/9/27
 */
@Component
public class EnvironmentConfig implements EnvironmentAware {

    @Getter
    private List<String> excludePathPatterns;

    @Override
    public void setEnvironment(Environment environment) {
        excludePathPatterns = environment.getProperty("chyern.session.exclude.path.patterns", List.class);
    }
}
