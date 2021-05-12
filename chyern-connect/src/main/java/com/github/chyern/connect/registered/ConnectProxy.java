package com.github.chyern.connect.registered;

import com.github.chyern.connect.Exception.ConnectException;
import com.github.chyern.connect.annotation.Connect;
import com.github.chyern.connect.annotation.method.DELETE;
import com.github.chyern.connect.annotation.method.GET;
import com.github.chyern.connect.annotation.method.POST;
import com.github.chyern.connect.annotation.method.PUT;
import com.github.chyern.connect.annotation.resource.Body;
import com.github.chyern.connect.annotation.resource.Path;
import com.github.chyern.connect.annotation.resource.Query;
import com.github.chyern.connect.processor.AbstractConnectProcessor;
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
        AbstractConnectProcessor handler = connect.clazz().newInstance();
        Map.Entry<String, String> entry = getHttpMethod(method);
        String url = connect.value() + entry.getValue();
        buildUrlByPath(url, method, args);
        buildUrlByQuery(url, method, args);
        Object body = getBody(method, args);
        Class<?> returnType = method.getReturnType();
        Field[] declaredFields = AbstractConnectProcessor.class.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> field.setAccessible(true));
        declaredFields[1].set(handler, new URL(url));
        declaredFields[2].set(handler, entry.getKey());
        declaredFields[4].set(handler, body);
        declaredFields[5].set(handler, returnType);
        return handler.execute();
    }

    private void buildUrlByPath(String url, Method method, Object[] args) throws ConnectException {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Path) {
                    String path = "{" + ((Path) annotation).value() + "}";
                    if (!url.contains(path)) {
                        throw new ConnectException("Could not bind the path " + path);
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
            url = url + "?" + queryStr;
        }
    }

    private Object getBody(Method method, Object[] args) throws Throwable {
        Object obj = null;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                Annotation annotation = parameterAnnotations[i][j];
                if (annotation instanceof Body) {
                    if (Objects.nonNull(obj)) {
                        throw new ConnectException("It is not allowed to define two request types on a single body");
                    }
                    obj = args[i];
                }
            }
        }
        return obj;
    }


    private Map.Entry<String, String> getHttpMethod(Method method) throws Throwable {
        List<Class<? extends Annotation>> classes = Arrays.asList(GET.class, POST.class, PUT.class, DELETE.class);
        List<Class<? extends Annotation>> collect = classes.stream().filter(method::isAnnotationPresent).collect(Collectors.toList());
        if (collect.size() != 1) {
            throw new ConnectException("It is not a single method");
        }
        Annotation annotation = method.getAnnotation(collect.get(0));
        String httpMethod = StringUtils.substringAfterLast(annotation.annotationType().getName(), ".");
        String value = (String) annotation.annotationType().getMethod("value").invoke(annotation);
        return new Map.Entry<String, String>() {
            @Override
            public String getKey() {
                return httpMethod;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public String setValue(String value) {
                return value;
            }
        };
    }
}
