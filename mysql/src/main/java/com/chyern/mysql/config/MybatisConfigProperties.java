package com.chyern.mysql.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/8 14:14
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "mybatis-config")
public class MybatisConfigProperties {

    private static final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

    private String[] mapperLocations = new String[]{"classpath*:/mapper/**/*.xml"};

    private boolean printSqlLog = false;

    private String[] interceptors;

    public Resource[] resolveMapperLocations() {
        return Stream.of(Optional.ofNullable(this.mapperLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return pathMatchingResourcePatternResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

    public Interceptor[] resolveInterceptor() {
        return Stream.of(Optional.ofNullable(this.interceptors).orElse(new String[0]))
                .flatMap(interceptor -> Stream.of(getInterceptor(interceptor))).toArray(Interceptor[]::new);
    }


    private Interceptor getInterceptor(String interceptorClass) {
        try {
            Class<?> forName = Class.forName(interceptorClass);
            return (Interceptor) forName.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
