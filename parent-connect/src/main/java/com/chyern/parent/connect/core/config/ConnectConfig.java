package com.chyern.parent.connect.core.config;

import com.chyern.parent.connect.processor.ConnectProcessor;
import com.chyern.parent.connect.core.registered.ConnectScanRegistrar;
import com.chyern.parent.connect.utils.ConnectUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/4/9 18:05
 */
@Configuration
public class ConnectConfig {

    @Bean
    public ConnectScanRegistrar connectScanRegistrar() {
        return new ConnectScanRegistrar();
    }

    @Bean
    public ConnectProcessor connectProcessor() {
        return new ConnectProcessor();
    }

    @Bean
    public ConnectUtil connectUtil() {
        return new ConnectUtil();
    }


}
