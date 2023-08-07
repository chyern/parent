package com.chenyudan.parent.connect.processor.utils;

import com.chenyudan.parent.connect.Connect;
import com.chenyudan.parent.connect.processor.annotation.method.RequestMapping;
import com.chenyudan.parent.connect.processor.annotation.resource.Body;
import com.chenyudan.parent.connect.processor.annotation.resource.Header;
import com.chenyudan.parent.connect.processor.annotation.resource.Path;
import com.chenyudan.parent.connect.processor.annotation.resource.Queries;
import com.chenyudan.parent.connect.processor.annotation.resource.Query;
import com.chenyudan.parent.connect.processor.domain.ConnectModel;
import com.chenyudan.parent.connect.processor.exception.ConnectErrorEnum;
import com.chenyudan.parent.core.constant.Constant;
import com.chenyudan.parent.core.utils.AssertUtil;
import com.chenyudan.parent.core.utils.BeanConvertUtil;
import com.chenyudan.parent.core.utils.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/28 14:45
 */
@Component
public class ConnectUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConnectUtil.context = applicationContext;
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
                    if (arg instanceof Map) {
                        Map<String, String> map = (Map<String, String>) arg;
                        headMap.putAll(map);
                    } else {
                        Map<String, Object> map = BeanConvertUtil.beanToMap(arg);
                        headMap.putAll(map.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> e.getValue().toString())));
                    }
                }
                //path
                if (annotation instanceof Path) {
                    Path path = (Path) annotation;
                    pathMap.put(path.value(), arg.toString());
                }
                //param
                if (annotation instanceof Queries) {
                    Queries queries = (Queries) annotation;
                    paramMap.put(queries.value(), arg);
                }
                if (annotation instanceof Query) {
                    Map<String, Object> map = BeanConvertUtil.beanToMap(arg);
                    paramMap.putAll(map);
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

        if (url.endsWith(Constant.OBLIQUE)) {
            url = StringUtil.substringBeforeLast(url, Constant.OBLIQUE);
        }
        if (requestUrl.startsWith(Constant.OBLIQUE)) {
            url = url + requestUrl;
        } else {
            url = url + Constant.OBLIQUE + requestUrl;
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
            value = StringUtil.substringBetween(value, "${", "}");
            if (value.contains(":")) {
                String substringBefore = StringUtil.substringBefore(value, ":");
                String substringAfter = StringUtil.substringAfter(value, ":");
                value = context.getEnvironment().getProperty(substringBefore, substringAfter);
            } else {
                boolean containsProperty = context.getEnvironment().containsProperty(value);
                AssertUtil.isTrue(containsProperty, ConnectErrorEnum.COULD_NOT_INSTANTIATION_KEY, key);
                value = context.getEnvironment().getProperty(value);
            }
        }
        return value;
    }
}
