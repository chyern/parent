package com.chyern.mysql;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/18 16:13
 */
@Configuration
public class DataSourceConfig {

    @Value("${mapper.locations}")
    private String mapperLocations;

    @Bean
    public CustomizedSqlInjector customizedSqlInjector() {
        return new CustomizedSqlInjector();
    }

    @ConfigurationProperties("spring.datasource.druid")
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));

        //mybatis全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(true);
        globalConfig.setSqlInjector(this.customizedSqlInjector());
        globalConfig.setDbConfig(new GlobalConfig.DbConfig());
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);

        //mybatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        configuration.setCacheEnabled(false);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);

        //mybatis拦截器
        List<Interceptor> interceptorList = new ArrayList<>();
        interceptorList.add(mybatisPlusInterceptor());
        mybatisSqlSessionFactoryBean.setPlugins(interceptorList.toArray(new Interceptor[0]));
        return mybatisSqlSessionFactoryBean.getObject();
    }

    private MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
