package com.github.chyern.connect.registered;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.utils.AssertUtil;
import com.github.chyern.connect.annotation.Connect;
import com.github.chyern.connect.annotation.method.RequestMapping;
import com.github.chyern.connect.annotation.resource.Body;
import com.github.chyern.connect.annotation.resource.Path;
import com.github.chyern.connect.annotation.resource.Query;
import com.github.chyern.connect.processor.AbstractConnectProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectProxy implements InvocationHandler {

    private ApplicationContext context;

    public ConnectProxy(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connect connect = method.getDeclaringClass().getAnnotation(Connect.class);
        AbstractConnectProcessor handler = connect.clazz().newInstance();
        String url = buildUrl(connect.value());
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        AssertUtil.isNull(requestMapping, ErrorEnum.CONNECT_METHOD_ERROR);
        url += requestMapping.value();
        buildUrlByPath(url, method, args);
        buildUrlByQuery(url, method, args);
        Object body = getBody(method, args);

        handler.init(new URI(url), requestMapping.method().name(), new HashMap<>(0), body, method.getReturnType());
        return handler.execute();
    }

    private String buildUrl(String str) {
        if (str.startsWith("${") && str.endsWith("}")) {
            String key = StringUtils.substringBetween(str, "${", "}");
            String[] split = key.split(":");
            if (context.getEnvironment().containsProperty(split[0])) {
                return context.getEnvironment().getProperty(split[0]);
            } else {
                AssertUtil.isTrue(split.length >= 2, ErrorEnum.CONNECT_URL_ERROR);
                return StringUtils.substringAfter(key, ":");
            }
        } else {
            return str;
        }
    }

    private void buildUrlByPath(String url, Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Path) {
                    String path = "{" + ((Path) annotation).value() + "}";
                    AssertUtil.isTrue(url.contains(path), ErrorEnum.CONNECT_PATH_ERROR);
                    url = url.replace(path, args[i].toString());
                }
            }
        }
    }


    private void buildUrlByQuery(String url, Method method, Object[] args) {
        LinkedHashMap<String, Object> queries = new LinkedHashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Query) {
                    queries.put(((Query) annotation).value(), args[i]);
                }
            }
        }
        if (queries.size() > 0) {
            String queryStr = StringUtils.join(queries.entrySet().stream().map(query -> query.getKey() + "=" + query.getValue()).collect(Collectors.toList()), "&");
            url += ("?" + queryStr);
        }
    }

    private Object getBody(Method method, Object[] args) {
        Object obj = null;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Body) {
                    AssertUtil.nonNull(obj, ErrorEnum.CONNECT_BODY_ERROR);
                    obj = args[i];
                }
            }
        }
        return obj;
    }

}
