package com.chyern.connect.analysis;

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
 * @since 2023/2/28 15:29
 */
@Data
public class ResourceAnalysis {

    private Map<String, String> heads;

    private Map<String, String> paths;

    private Map<String, Object> params;

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
