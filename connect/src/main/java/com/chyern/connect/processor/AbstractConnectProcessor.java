package com.chyern.connect.processor;

import com.chyern.connect.annotation.Connect;
import com.chyern.connect.annotation.method.MediaType;
import com.chyern.connect.annotation.method.RequestMapping;
import com.chyern.connect.annotation.resource.Body;
import com.chyern.connect.annotation.resource.Header;
import com.chyern.connect.annotation.resource.Path;
import com.chyern.connect.annotation.resource.Query;
import com.chyern.core.utils.AssertUtil;
import com.chyern.spicore.exception.ConnectErrorEnum;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 22:39
 */
public abstract class AbstractConnectProcessor implements IConnectProcessor, ApplicationContextAware {

    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AbstractConnectProcessor.applicationContext = applicationContext;
    }

    @Override
    public void before(Object proxy, Method method, Object[] args) throws Throwable {

    }

    @Override
    public Object execute(Object proxy, Method method, Object[] args) throws Throwable {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String url = buildUrl(method, args);
        Map<String, String> headerMap = buildHeader(method, args);
        String bodyStr = buildBody(method, args);
        //okhttp连接
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10L, TimeUnit.SECONDS).callTimeout(30L, TimeUnit.SECONDS).build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse(requestMapping.mediaType().getValue());
        RequestBody body = RequestBody.create(mediaType, bodyStr);
        com.chyern.connect.annotation.method.Method requestMethod = requestMapping.method();
        if (com.chyern.connect.annotation.method.Method.GET.equals(requestMethod)) {
            body = null;
        }
        Request.Builder builder = new Request.Builder().url(url).method(requestMapping.method().toString(), body);
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            builder = builder.addHeader(entry.getKey(), entry.getValue());
        }
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        this.beforeReturnExecute(response);
        AssertUtil.isTrue(response.isSuccessful(), ConnectErrorEnum.CONNECT_ERROR);
        ResponseBody responseBody = response.body();
        if (Objects.isNull(responseBody) || void.class.equals(method.getReturnType())) {
            return null;
        }
        if (String.class.equals(method.getReturnType())) {
            return responseBody.string();
        }
        return new GsonBuilder().create().fromJson(responseBody.charStream(), method.getGenericReturnType());
    }

    @Override
    public void after(Object proxy, Method method, Object[] args, Object result) throws Throwable {

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
        AssertUtil.isTrue(headerAnnotationNum.get() <= 1, ConnectErrorEnum.CONNECT_HEADER_ERROR);
        HashMap<String, String> map = new HashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            if (Arrays.stream(parameterAnnotation).noneMatch(annotation -> annotation instanceof Header)) {
                continue;
            }
            Object arg = args[i];
            AssertUtil.isTrue(arg instanceof Map, ConnectErrorEnum.CONNECT_HEADER_TYPE_ERROR);
            Map<Object, Object> argMap = (Map) arg;
            for (Map.Entry<Object, Object> entry : argMap.entrySet()) {
                map.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return map;
    }

    protected String buildBody(Method method, Object[] args) throws Throwable {
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
        AssertUtil.isTrue(bodyAnnotationNum.get() <= 1, ConnectErrorEnum.CONNECT_BODY_ERROR);
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
            if (MediaType.JSON.getValue().equals(method.getAnnotation(RequestMapping.class).mediaType().getValue())) {
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

    protected String replaceValue(String value) {
        String newValue = value;
        if (newValue.startsWith("${") && newValue.endsWith("}")) {
            newValue = StringUtils.substringBetween(newValue, "${", "}");
            if (newValue.contains(":")) {
                String substringBefore = StringUtils.substringBefore(newValue, ":");
                String substringAfter = StringUtils.substringAfter(newValue, ":");
                newValue = applicationContext.getEnvironment().getProperty(substringBefore, substringAfter);
            } else {
                AssertUtil.isTrue(applicationContext.getEnvironment().containsProperty(newValue), ConnectErrorEnum.COULD_NOT_INSTANTIATION_KEY, value);
                newValue = applicationContext.getEnvironment().getProperty(newValue);
            }
        }
        return newValue;
    }

    protected String buildUrl(Method method, Object[] args) {
        Connect connect = method.getDeclaringClass().getAnnotation(Connect.class);
        String url = replaceValue(connect.value());
        //拼接RequestMapping上url
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String value = requestMapping.value();
        url += value;
        //替换RequestMapping上注解
        if (Objects.isNull(args)) {
            return url;
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (Objects.isNull(arg)) {
                continue;
            }
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof Path) {
                    Path path = (Path) annotation;
                    url = StringUtils.replace(url, "{" + path.value() + "}", arg.toString());
                }
            }
        }
        url += buildGetParams(method, args);
        return url;
    }


    protected void beforeReturnExecute(Object obj) {

    }
}
