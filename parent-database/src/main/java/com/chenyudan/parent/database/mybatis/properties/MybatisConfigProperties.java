package com.chenyudan.parent.database.mybatis.properties;

import com.chenyudan.parent.database.mybatis.interceptor.DefaultInterceptor;
import com.chenyudan.parent.database.mybatis.interceptor.Interceptor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/2/8 14:14
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "mybatis-config")
public class MybatisConfigProperties {

    private static final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

    /**
     * mapper文件扫描路径
     */
    private String[] mapperLocations = new String[]{"classpath*:/mybatis/**/*Mapper.xml", "classpath*:/mybatis/*Mapper.xml"};

    /**
     * sql拦截器
     */
    private Class<? extends Interceptor> interceptor = DefaultInterceptor.class;

    /**
     * 打印日志实现类
     */
    private Class<? extends Log> logImpl = Slf4jImpl.class;

    /**
     * 获取扫描路径下的mapper资源
     */
    public Resource[] resolveMapperLocations() {
        return Stream.of(Optional.ofNullable(this.mapperLocations).orElse(new String[0])).flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return pathMatchingResourcePatternResolver.getResources(location);
        } catch (Exception e) {
            return new Resource[0];
        }
    }


    /**
     * 获取拦截器
     */
    public org.apache.ibatis.plugin.Interceptor[] resolveInterceptor() {
        try {
            return interceptor.getDeclaredConstructor().newInstance().resolveInterceptor();
        } catch (Exception e) {
            return new org.apache.ibatis.plugin.Interceptor[0];
        }
    }


}
