package com.chyern.parent.connect.utils;

import com.chyern.parent.api.exception.enums.ConnectErrorEnum;
import com.chyern.parent.connect.annotation.Connect;
import com.chyern.parent.connect.annotation.method.RequestMapping;
import com.chyern.parent.connect.annotation.resource.Body;
import com.chyern.parent.connect.annotation.resource.Header;
import com.chyern.parent.connect.annotation.resource.Path;
import com.chyern.parent.connect.annotation.resource.Query;
import com.chyern.parent.connect.domain.ConnectModel;
import com.chyern.parent.core.constant.CoreConstant;
import com.chyern.parent.core.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/28 14:45
 */
@Component
public class ConnectUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@RequestParam ApplicationContext applicationContext) throws BeansException {
        ConnectUtil.applicationContext = applicationContext;
    }

    /**
     * 构建ConnectModel对象
     *
     * @param method 方法
     * @param args   参数
     */
    public static ConnectModel buildBy(Method method, Object[] args) {
        ConnectModel connectModel = new ConnectModel();
        if (args == null) {
            return connectModel;
        }

        //解析方法上注解
        Connect connect = method.getDeclaringClass().getAnnotation(Connect.class);
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        buildBy(connectModel, connect, requestMapping);

        //解析参数上注解
        Map<String, String> headMap = new HashMap<>();
        Map<String, String> pathMap = new HashMap<>();
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            for (Annotation annotation : parameterAnnotations[i]) {
                //head
                if (annotation instanceof Header) {
                    AssertUtil.isTrue(arg instanceof Map, ConnectErrorEnum.CONNECT_HEADER_TYPE_ERROR);
                    Map<String, String> map = (Map<String, String>) arg;
                    headMap.putAll(map);
                }
                //path
                if (annotation instanceof Path) {
                    Path path = (Path) annotation;
                    pathMap.put(path.value(), arg.toString());
                }
                //param
                if (annotation instanceof Query) {
                    Query query = (Query) annotation;
                    paramMap.put(query.value(), arg);
                }
                //body
                if (annotation instanceof Body) {
                    AssertUtil.isTrue(connectModel.getBody() == null, ConnectErrorEnum.CONNECT_BODY_ERROR);
                    connectModel.setBody(arg);
                }
            }
        }
        connectModel.setHeads(headMap);
        connectModel.setPaths(pathMap);
        connectModel.setParams(paramMap);
        return connectModel;
    }

    private static void buildBy(ConnectModel connectModel, Connect connect, RequestMapping requestMapping) {
        String url = analysisProperty(connect.value());
        String requestUrl = requestMapping.value();

        if (url.endsWith(CoreConstant.OBLIQUE)) {
            url = StringUtils.substringBeforeLast(url, CoreConstant.OBLIQUE);
        }
        if (requestUrl.startsWith(CoreConstant.OBLIQUE)) {
            url = url + requestUrl;
        } else {
            url = url + CoreConstant.OBLIQUE + requestUrl;
        }

        connectModel.setUrl(url);
        connectModel.setMethod(requestMapping.method());
        connectModel.setMediaType(requestMapping.mediaType());
    }


    /**
     * 解析配置项
     * 支持${key},${key:}
     */
    private static String analysisProperty(String key) {
        String value = key;
        if (value.startsWith("${") && value.endsWith("}")) {
            value = StringUtils.substringBetween(value, "${", "}");
            if (value.contains(":")) {
                String substringBefore = StringUtils.substringBefore(value, ":");
                String substringAfter = StringUtils.substringAfter(value, ":");
                value = applicationContext.getEnvironment().getProperty(substringBefore, substringAfter);
            } else {
                boolean containsProperty = applicationContext.getEnvironment().containsProperty(value);
                AssertUtil.isTrue(containsProperty, ConnectErrorEnum.COULD_NOT_INSTANTIATION_KEY, key);
                value = applicationContext.getEnvironment().getProperty(value);
            }
        }
        return value;
    }
}
