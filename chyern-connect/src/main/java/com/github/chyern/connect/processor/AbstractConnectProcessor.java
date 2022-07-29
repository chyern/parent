package com.github.chyern.connect.processor;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.utils.AssertUtil;
import com.github.chyern.connect.annotation.method.RequestMapping;
import com.github.chyern.connect.annotation.resource.Body;
import com.github.chyern.connect.annotation.resource.Header;
import com.github.chyern.connect.annotation.resource.Query;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 22:39
 */
public abstract class AbstractConnectProcessor implements IConnectProcessor {

    @Override
    public void before(Object proxy, Method method, Object[] args) throws Exception {

    }

    @Override
    public void after(Object proxy, Method method, Object[] args, Object result) throws Exception {

    }

    @Override
    public void throwsException(Object proxy, Method method, Object[] args, Exception exception) {

    }


    protected String buildGetParams(Method method, Object[] args) {
        String getParams = "";
        if (Objects.isNull(args)) {
            return getParams;
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        List<String> paramList = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (Objects.isNull(arg)) {
                continue;
            }
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof Query) {
                    Query query = (Query) annotation;
                    paramList.add(query.value() + "=" + arg.toString());
                }
            }
        }
        if (!paramList.isEmpty()) {
            String paramsStr = StringUtils.join(paramList, "&");
            getParams += ("?" + paramsStr);
        }
        return getParams;
    }

    protected Map<String, String> buildHeader(Method method, Object[] args) {
        if (Objects.isNull(args)) {
            return new HashMap<>();
        }
        AtomicInteger headerAnnotationNum = new AtomicInteger();
        Arrays.stream(method.getParameters()).forEach(parameter -> {
            Arrays.stream(parameter.getAnnotations()).forEach(annotation -> {
                if (annotation instanceof Header) {
                    headerAnnotationNum.getAndIncrement();
                }
            });
        });
        AssertUtil.isTrue(headerAnnotationNum.get() <= 1, ErrorEnum.CONNECT_HEADER_ERROR);
        HashMap<String, String> map = new HashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            if (Arrays.stream(parameterAnnotation).noneMatch(annotation -> annotation instanceof Header)) {
                continue;
            }
            Object arg = args[i];
            AssertUtil.isTrue(arg instanceof Map, ErrorEnum.CONNECT_HEADER_TYPE_ERROR);
            Map<Object, Object> argMap = (Map) arg;
            for (Map.Entry<Object, Object> entry : argMap.entrySet()) {
                map.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return map;
    }

    protected String buildBody(Method method, Object[] args) throws Exception {
        String bodyStr = "";
        if (Objects.isNull(args)) {
            return bodyStr;
        }
        AtomicInteger bodyAnnotationNum = new AtomicInteger();
        Arrays.stream(method.getParameters()).forEach(parameter -> {
            Arrays.stream(parameter.getAnnotations()).forEach(annotation -> {
                if (annotation instanceof Body) {
                    bodyAnnotationNum.getAndIncrement();
                }
            });
        });
        AssertUtil.isTrue(bodyAnnotationNum.get() <= 1, ErrorEnum.CONNECT_BODY_ERROR);
        if (bodyAnnotationNum.get() == 0) {
            return bodyStr;
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            if (Arrays.stream(parameterAnnotation).noneMatch(annotation -> annotation instanceof Body)) {
                continue;
            }
            Object arg = args[i];
            if (com.github.chyern.connect.annotation.method.MediaType.JSON.getValue().equals(method.getAnnotation(RequestMapping.class).mediaType().getValue())) {
                bodyStr = new GsonBuilder().create().toJson(arg);
            } else {
                List<String> fieldList = new ArrayList<>();
                Field[] declaredFields = arg.getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    String name = declaredField.getName();
                    if ("serialVersionUID".equals(name)) {
                        continue;
                    }
                    declaredField.setAccessible(true);
                    Object value = declaredField.get(arg);
                    fieldList.add(name + "=" + value);
                }
                bodyStr = StringUtils.join(fieldList, "&");
            }
            break;
        }
        return bodyStr;
    }
}
