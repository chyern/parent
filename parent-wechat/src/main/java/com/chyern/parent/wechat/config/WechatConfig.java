package com.chyern.parent.wechat.config;

import com.chyern.parent.connect.ConnectScan;
import com.chyern.parent.connect.processor.connect.annotation.Connect;
import com.chyern.parent.connect.processor.connect.processor.ConnectProcessor;
import com.chyern.parent.wechat.connect.common.process.WechatConnectProcessor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/1/11 15:16
 */
@ConnectScan(value = {"com.chyern.wechat.connect"}, annotation = Connect.class, connectProcessor = ConnectProcessor.class)
@Import({WechatConnectProcessor.class})
@Component
public class WechatConfig {
}
