package com.chyern.mysql.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.chyern.mysql.injector.SqlInjector;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/18 16:13
 */
@Configuration
@EnableConfigurationProperties({MybatisConfigProperties.class})
@AutoConfigureBefore(DruidDataSourceAutoConfigure.class)
public class DataSourceConfig {

    @ConfigurationProperties("spring.datasource.druid")
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisConfigProperties mybatisConfigProperties) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(mybatisConfigProperties.resolveMapperLocations());


        //mybatis全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setSqlInjector(new SqlInjector());
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);

        //mybatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        if (mybatisConfigProperties.isPrintSqlLog()) {
            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        }
        configuration.setCacheEnabled(false);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);

        //mybatis拦截器
        Interceptor[] interceptors = ArrayUtils.add(mybatisConfigProperties.resolveInterceptor(), mybatisPlusInterceptor());
        mybatisSqlSessionFactoryBean.setPlugins(interceptors);
        return mybatisSqlSessionFactoryBean.getObject();
    }

    private MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
