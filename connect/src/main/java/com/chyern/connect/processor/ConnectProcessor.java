package com.chyern.connect.processor;

import com.chyern.connect.annotation.Connect;
import com.chyern.connect.annotation.method.RequestMapping;
import com.chyern.connect.annotation.resource.Path;
import com.chyern.connect.exception.ConnectErrorEnum;
import com.chyern.core.utils.AssertUtil;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
@Component
public class ConnectProcessor extends AbstractConnectProcessor {

    @Override
    public Object execute(Object proxy, Method method, Object[] args) throws Throwable {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String url = buildUrl(method, args);
        Map<String, String> headerMap = buildHeader(method, args);
        String bodyStr = buildBody(method, args);
        //okhttp连接
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10L, TimeUnit.SECONDS).callTimeout(30L, TimeUnit.SECONDS).build();
        MediaType mediaType = MediaType.parse(requestMapping.mediaType().getValue());
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

    private static String replaceValue(String value) {
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

    private String buildUrl(Method method, Object[] args) {
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
        url += super.buildGetParams(method, args);
        return url;
    }


    protected void beforeReturnExecute(Object obj) {

    }

}
