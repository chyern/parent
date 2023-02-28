package com.chyern.connect.utils;

import com.chyern.connect.analysis.MethodAnalysis;
import com.chyern.connect.analysis.ResourceAnalysis;
import com.chyern.connect.annotation.Connect;
import com.chyern.connect.annotation.method.RequestMapping;
import com.chyern.connect.annotation.resource.Body;
import com.chyern.connect.annotation.resource.Header;
import com.chyern.connect.annotation.resource.Path;
import com.chyern.connect.annotation.resource.Query;
import com.chyern.connect.constant.MediaType;
import com.chyern.core.constant.CoreConstant;
import com.chyern.core.utils.AssertUtil;
import com.chyern.spicore.exception.ConnectErrorEnum;
import com.google.gson.GsonBuilder;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConnectUtil.applicationContext = applicationContext;
    }

    public static String buildConnectUrl(Connect connect) {
        return analysisProperty(connect.value());
    }

    public static MethodAnalysis buildMethodAnalysis(RequestMapping requestMapping) {
        return new MethodAnalysis(requestMapping.value(), requestMapping.method(), requestMapping.mediaType());
    }

    public static ResourceAnalysis buildResourceAnalysis(Method method, Object[] args) {
        ResourceAnalysis resourceAnalysis = new ResourceAnalysis();
        buildResourceAnalysis(resourceAnalysis, method, args);
        return resourceAnalysis;
    }

    public static String buildBodyStr(MediaType mediaType, Object body) throws InvocationTargetException, IllegalAccessException {
        String bodyStr = null;
        if (body == null) {
            return bodyStr;
        }
        if (MediaType.X_WWW_FORM_URLENCODED.equals(mediaType)) {
            BeanMap beanMap = new BeanMap(body);
            List<String> collect = beanMap.entrySet().stream().map(entry -> {
                Object key = entry.getKey();
                Object value = entry.getValue();
                return key + CoreConstant.EQUAL_SIGN + value;
            }).collect(Collectors.toList());
            bodyStr = StringUtils.join(collect, "&");
        } else {
            bodyStr = new GsonBuilder().create().toJson(body);
        }
        return bodyStr;
    }


    private static void buildResourceAnalysis(ResourceAnalysis resourceAnalysis, Method method, Object[] args) {
        if (args == null) {
            return;
        }

        Map<String, String> headMap = new HashMap<>();
        Map<String, String> pathMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            //head
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof Header) {
                    AssertUtil.isTrue(arg instanceof Map, ConnectErrorEnum.CONNECT_HEADER_TYPE_ERROR);
                    Map<String, String> map = (Map<String, String>) arg;
                    headMap.putAll(map);
                }
            }
            //path
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof Path) {
                    Path path = (Path) annotation;
                    pathMap.put(path.value(), arg.toString());
                }
            }
            //param
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof Query) {
                    Query query = (Query) annotation;
                    paramMap.put(query.value(), arg);
                }
            }
            //body
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof Body) {
                    AssertUtil.isTrue(resourceAnalysis.getBody() == null, ConnectErrorEnum.CONNECT_HEADER_TYPE_ERROR);
                    resourceAnalysis.setBody(arg);
                }
            }
        }
        resourceAnalysis.setHeads(headMap);
        resourceAnalysis.setPaths(pathMap);
        resourceAnalysis.setParams(paramMap);
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
