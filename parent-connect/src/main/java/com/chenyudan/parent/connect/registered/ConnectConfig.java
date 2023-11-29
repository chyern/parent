package com.chenyudan.parent.connect.registered;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2023/4/10 19:56
 */
@Configuration
@ComponentScan({"com.chenyudan.parent.connect.processor","com.chenyudan.parent.connect.registered"})
public class ConnectConfig {
}
