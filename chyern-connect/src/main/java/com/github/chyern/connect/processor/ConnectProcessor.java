package com.github.chyern.connect.processor;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.utils.AssertUtil;
import com.github.chyern.connect.annotation.Connect;
import com.github.chyern.connect.annotation.method.RequestMapping;
import com.github.chyern.connect.annotation.resource.Path;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
@Component
public class ConnectProcessor extends AbstractConnectProcessor {

    @Resource
    private ApplicationContext context;

    @Override
    public Object execute(Object proxy, Method method, Object[] args) throws Exception {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String url = buildUrl(method, args);
        Map<String, String> headerMap = buildHeader(method, args);
        String bodyStr = buildBody(method, args);
        //okhttp连接
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse(requestMapping.mediaType().getValue());
        RequestBody body = RequestBody.create(mediaType, bodyStr);
        com.github.chyern.connect.annotation.method.Method requestMethod = requestMapping.method();
        if (com.github.chyern.connect.annotation.method.Method.GET.equals(requestMethod)) {
            body = null;
        }
        Request.Builder builder = new Request.Builder().url(url).method(requestMapping.method().toString(), body);
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            builder = builder.addHeader(entry.getKey(), entry.getValue());
        }
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        this.beforeReturnExecute(response);
        ResponseBody responseBody = response.body();
        if (Objects.isNull(responseBody)) {
            return null;
        }
        String responseStr = responseBody.string();
        Class<?> returnType = method.getReturnType();
        if (String.class.equals(returnType)) {
            return responseStr;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create().fromJson(responseStr, returnType);
    }

    private String buildUrl(Method method, Object[] args) {
        String url = "";
        //取Connect上url
        Connect connect = method.getDeclaringClass().getAnnotation(Connect.class);
        url += connect.value();
        if (url.startsWith("${") && url.endsWith("}")) {
            url = StringUtils.substringBetween(url, "${", "}");
            String[] split = StringUtils.split(url, ":");
            AssertUtil.isTrue(split.length <= 2, ErrorEnum.CONNECT_URL_ERROR);
            if (context.getEnvironment().containsProperty(split[0])) {
                url = context.getEnvironment().getProperty(split[0]);
            } else {
                url = StringUtils.substringAfter(url, ":");
            }
        }
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


    private void beforeReturnExecute(Object obj) {

    }

}
