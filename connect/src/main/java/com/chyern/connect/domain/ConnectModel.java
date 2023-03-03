package com.chyern.connect.domain;

import com.chyern.connect.constant.MediaType;
import com.chyern.connect.constant.Method;
import com.chyern.core.constant.CoreConstant;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
     * 请求方式
     */
    private Method method;

    /**
     * 请求格式
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
            paramList.add(key + CoreConstant.EQUAL_SIGN + value);
        }

        return StringUtils.join(paramList, CoreConstant.AMPERSAND);
    }
}
