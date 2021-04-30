package com.chyern.connect.registered;

import com.github.chyern.connect.annotations.Body;
import com.github.chyern.connect.annotations.Headers;
import com.github.chyern.connect.annotations.Query;
import com.github.chyern.connect.annotations.method.DELETE;
import com.github.chyern.connect.annotations.method.GET;
import com.github.chyern.connect.annotations.method.POST;
import com.github.chyern.connect.annotations.method.PUT;
import com.github.chyern.connect.handler.AbstractConnectHandler;
import com.github.chyern.connect.scan.Connect;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectProxy implements InvocationHandler {

    private static final String REGEX = "\\$\\{.*\\}";

    private final ApplicationContext applicationContext;

    public ConnectProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connect connect = method.getDeclaringClass().getAnnotation(Connect.class);
        AbstractConnectHandler handler = connect.clazz().newInstance();

        String uri = connect.value();
        Matcher matcher = Pattern.compile(REGEX).matcher(uri);
        if (matcher.find()) {
            Environment environment = applicationContext.getEnvironment();
            String envStr = environment.getProperty(matcher.group().substring(2, matcher.group().length() - 1));
            if (StringUtils.isBlank(envStr)) {
                throw new Throwable();
            }
            uri = envStr;
        }
        Map<String, String> httpMethodMap = getHttpMethod(method);
        uri += httpMethodMap.values().stream().findFirst().get();
        String httpMethod = httpMethodMap.keySet().stream().findFirst().get();
        Map<String, String> headers = getHeaders(method, args);
        Map<String, Object> query = getQuery(method, args);
        Object body = getBody(method, args);
        Class<?> returnType = method.getReturnType();

        Field[] declaredFields = AbstractConnectHandler.class.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> field.setAccessible(true));
        declaredFields[0].set(handler, uri);
        declaredFields[1].set(handler, httpMethod);
        declaredFields[2].set(handler, headers);
        declaredFields[3].set(handler, query);
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

    private Map<String, Object> getQuery(Method method, Object[] args) throws Throwable {
        Map<String, Object> query = new HashMap<>();
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
