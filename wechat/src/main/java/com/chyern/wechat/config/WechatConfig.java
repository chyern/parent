package com.chyern.wechat.config;

import com.chyern.connect.ConnectScan;
import com.chyern.wechat.connect.common.process.WechatConnectProcessor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:16
 */
@ConnectScan(basePackages = "com.chyern.wechat.connect")
@Import({WechatConnectProcessor.class})
@Component
public class WechatConfig {
}
