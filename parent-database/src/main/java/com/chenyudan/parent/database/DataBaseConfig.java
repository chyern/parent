package com.chenyudan.parent.database;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.chenyudan.parent.database.datasource.DataSourceSwitchAspect;
import com.chenyudan.parent.database.datasource.RoutingDataSource;
import com.chenyudan.parent.database.mybatis.injector.SqlInjector;
import com.chenyudan.parent.database.mybatis.properties.MybatisConfigProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/18 16:13
 */
@Configuration
@EnableTransactionManagement
@AutoConfigureBefore(DruidDataSourceAutoConfigure.class)
@EnableConfigurationProperties({MybatisConfigProperties.class})
@Import({DataSourceSwitchAspect.class})
public class DataBaseConfig {

    @Primary
    @ConfigurationProperties("spring.datasource.druid")
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "routingDataSource")
    public RoutingDataSource routingDataSource(DataSource dataSource, Map<String, DataSource> dataSourceMap) {
        return new RoutingDataSource(dataSource, dataSourceMap);
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(RoutingDataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(RoutingDataSource routingDataSource, MybatisConfigProperties mybatisConfigProperties) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(routingDataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(mybatisConfigProperties.resolveMapperLocations());


        //mybatis全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setSqlInjector(new SqlInjector());
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);

        //mybatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(mybatisConfigProperties.getLogImpl());
        configuration.setCacheEnabled(false);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);

        //mybatis拦截器
        mybatisSqlSessionFactoryBean.setPlugins(mybatisConfigProperties.resolveInterceptor());
        return mybatisSqlSessionFactoryBean.getObject();
    }

}

