package com.github.chyern.connect.registered;

import com.github.chyern.common.enums.ChyernErrorEnum;
import com.github.chyern.common.exception.ChyernException;
import com.github.chyern.connect.annotation.Connect;
import com.github.chyern.connect.annotation.method.RequestMapping;
import com.github.chyern.connect.annotation.resource.Body;
import com.github.chyern.connect.annotation.resource.Path;
import com.github.chyern.connect.annotation.resource.Query;
import com.github.chyern.connect.processor.AbstractConnectProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;
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
        if (requestMapping == null) {
            throw new ChyernException(ChyernErrorEnum.CONNECT_METHOD_ERROR);
        }
        url += requestMapping.value();
        buildUrlByPath(url, method, args);
        buildUrlByQuery(url, method, args);
        Object body = getBody(method, args);

        Field[] declaredFields = AbstractConnectProcessor.class.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> field.setAccessible(true));
        declaredFields[1].set(handler, new URI(url));
        declaredFields[2].set(handler, requestMapping.method().name());
        declaredFields[4].set(handler, body);
        declaredFields[5].set(handler, method.getReturnType());
        return handler.execute();
    }

    private String buildUrl(String str) {
        if (str.startsWith("${") && str.endsWith("}")) {
            String key = StringUtils.substringBetween(str, "${", "}");
            String[] split = key.split(":");
            if (context.getEnvironment().containsProperty(split[0])) {
                return context.getEnvironment().getProperty(split[0]);
            } else {
                if (split.length < 2) {
                    throw new ChyernException(ChyernErrorEnum.CONNECT_URL_ERROR);
                }
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
                    if (!url.contains(path)) {
                        throw new ChyernException(ChyernErrorEnum.CONNECT_PATH_ERROR);
                    }
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
                    if (Objects.nonNull(obj)) {
                        throw new ChyernException(ChyernErrorEnum.CONNECT_BODY_ERROR);
                    }
                    obj = args[i];
                }
            }
        }
        return obj;
    }

}
