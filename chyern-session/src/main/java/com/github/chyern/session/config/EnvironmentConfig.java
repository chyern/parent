package com.github.chyern.session.config;

import lombok.Getter;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/9/27
 */
@Component
public class EnvironmentConfig implements EnvironmentAware {

    public static final String EXCLUDE_PATH_KEY = "session.exclude.path.patterns";

    @Getter
    private List<String> excludePathPatterns;

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;

        excludePathPatterns = getPropertyList(EXCLUDE_PATH_KEY);
    }

    private String getProperty(String key) {
        return env.getProperty(key);
    }

    private List<String> getPropertyList(String key) {
        BindResult<List<String>> bind = Binder.get(env).bind(key, Bindable.listOf(String.class));
        return bind.orElse(new ArrayList<>());
    }
}
