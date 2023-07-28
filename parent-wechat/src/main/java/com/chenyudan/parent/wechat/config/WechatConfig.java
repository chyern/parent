package com.chenyudan.parent.wechat.config;

import com.chenyudan.parent.connect.ConnectScan;
import com.chenyudan.parent.wechat.connect.common.process.WechatConnectProcessor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:16
 */
@ConnectScan(value = "com.chenyudan.wechat.connect")
@Import({WechatConnectProcessor.class})
@Component
public class WechatConfig {
}
