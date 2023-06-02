package com.chenyudan.parent.wechat.config;

import com.chenyudan.parent.connect.ConnectScan;
import com.chenyudan.parent.connect.processor.connect.Connect;
import com.chenyudan.parent.connect.processor.connect.ConnectProcessor;
import com.chenyudan.parent.wechat.connect.common.process.WechatConnectProcessor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:16
 */
@ConnectScan(value = {"com.chenyudan.wechat.connect"}, annotation = Connect.class, connectProcessor = ConnectProcessor.class)
@Import({WechatConnectProcessor.class})
@Component
public class WechatConfig {
}
