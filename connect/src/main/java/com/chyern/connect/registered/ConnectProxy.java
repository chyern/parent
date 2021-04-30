package com.chyern.connect.registered;

import com.chyern.connect.annotations.Body;
import com.chyern.connect.annotations.Headers;
import com.chyern.connect.annotations.Query;
import com.chyern.connect.annotations.method.DELETE;
import com.chyern.connect.annotations.method.GET;
import com.chyern.connect.annotations.method.POST;
import com.chyern.connect.annotations.method.PUT;
import com.chyern.connect.handler.AbstractConnectHandler;
import com.chyern.connect.scan.Connect;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connect connect = method.getDeclaringClass().getAnnotation(Connect.class);
        AbstractConnectHandler handler = connect.clazz().newInstance();
        Map<String, String> httpMethodMap = getHttpMethod(method);
        String urlStr = connect.value() + httpMethodMap.values().stream().findFirst().get();
        URL url = new URL(urlStr);
        LinkedHashMap<String, Object> queries = getQuery(method, args);
        if (queries.size() > 0) {
            String queryStr = StringUtils.join(queries.entrySet().stream().map(query -> query.getKey() + "=" + query.getValue()).collect(Collectors.toList()), "&");
            url = new URL(urlStr + "?" + queryStr);
        }
        String httpMethod = httpMethodMap.keySet().stream().findFirst().get();
        Map<String, String> headers = getHeaders(method, args);
        Object body = getBody(method, args);
        Class<?> returnType = method.getReturnType();
        Field[] declaredFields = AbstractConnectHandler.class.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> field.setAccessible(true));
        declaredFields[1].set(handler, url);
        declaredFields[2].set(handler, httpMethod);
        declaredFields[3].set(handler, headers);
        declaredFields[4].set(handler, body);
        declaredFields[5].set(handler, returnType);
        return handler.execute();
    }

    private Map<String, String> getHeaders(Method method, Object[] args) throws Throwable {
        Map<String, String> headers = new HashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Headers) {
                    if (headers.size() != 0) {
                        throw new Throwable();
                    }
                    try {
                        headers.putAll((Map<String, String>) args[i]);
                    } catch (Exception e) {
                        throw new Throwable();
                    }
                }
            }
        }
        return headers;
    }

    private LinkedHashMap<String, Object> getQuery(Method method, Object[] args) throws Throwable {
        LinkedHashMap<String, Object> query = new LinkedHashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Query) {
                    try {
                        query.put(((Query) annotation).value(), args[i]);
                    } catch (Exception e) {
                        throw new Throwable();
                    }
                }
            }
        }
        return query;
    }

    private Object getBody(Method method, Object[] args) throws Throwable {
        Object obj = null;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Body) {
                    if (Objects.nonNull(obj)) {
                        throw new Throwable();
                    }
                    obj = args[i];
                }
            }
        }
        return obj;
    }


    private Map<String, String> getHttpMethod(Method method) throws Throwable {
        Map<String, String> httpMethodMap = new HashMap<>();
        List<Class<? extends Annotation>> classes = Arrays.asList(GET.class, POST.class, PUT.class, DELETE.class);
        for (Class<? extends Annotation> aClass : classes) {
            if (method.isAnnotationPresent(aClass)) {
                Annotation annotation = method.getAnnotation(aClass);
                String httpMethod = StringUtils.substringAfterLast(annotation.annotationType().getName(), ".");
                String value = (String) annotation.annotationType().getMethod("value").invoke(annotation);
                httpMethodMap.put(httpMethod, value);
            }
        }
        if (httpMethodMap.size() != 1) {
            throw new Throwable();
        }
        return httpMethodMap;
    }
}
