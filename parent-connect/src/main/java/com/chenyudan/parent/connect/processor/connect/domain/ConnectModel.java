package com.chenyudan.parent.connect.processor.connect.domain;

import com.chenyudan.parent.connect.processor.connect.constant.MediaType;
import com.chenyudan.parent.connect.processor.connect.constant.Method;
import com.chenyudan.parent.core.constant.Constant;
import com.google.gson.GsonBuilder;
import lombok.Data;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/28 15:25
 */
@Data
public class ConnectModel {

    /**
     * 方法url
     */
    private String url;

    /**
     * 方法类型
     */
    private Method method;

    /**
     * 请求类型
     */
    private MediaType mediaType;

    /**
     * 请求头
     */
    private Map<String, String> heads;

    /**
     * path参数
     * AAA/{path}/BBB
     */
    private Map<String, String> paths;

    /**
     * param参数
     * ?a=xxx&b=xxx
     */
    private Map<String, Object> params;

    /**
     * body参数
     */
    private Object body;

    /**
     * 解析param参数为url
     * ?a=xxx&b=xxx
     */
    public String getParamsUrl() {
        if (params == null) {
            return StringUtils.EMPTY;
        }

        List<String> paramList = new ArrayList<>();
        for (Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            paramList.add(key + Constant.EQUAL_SIGN + value);
        }

        return StringUtils.join(paramList, Constant.AMPERSAND);
    }

    /**
     * 获取body的字符串
     */
    public String getBodyStr() {
        String bodyStr = null;
        if (body == null) {
            return bodyStr;
        }
        if (MediaType.X_WWW_FORM_URLENCODED.equals(mediaType)) {
            BeanMap beanMap = new BeanMap(body);
            List<String> collect = beanMap.entrySet().stream().map(entry -> {
                Object key = entry.getKey();
                Object value = entry.getValue();
                return key + Constant.EQUAL_SIGN + value;
            }).collect(Collectors.toList());
            bodyStr = StringUtils.join(collect, "&");
        } else {
            bodyStr = new GsonBuilder().create().toJson(body);
        }
        return bodyStr;
    }
}
