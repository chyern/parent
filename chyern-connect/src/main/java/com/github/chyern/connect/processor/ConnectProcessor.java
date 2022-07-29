package com.github.chyern.connect.processor;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.utils.AssertUtil;
import com.github.chyern.connect.annotation.Connect;
import com.github.chyern.connect.annotation.method.RequestMapping;
import com.github.chyern.connect.annotation.resource.Body;
import com.github.chyern.connect.annotation.resource.Path;
import com.github.chyern.connect.annotation.resource.Query;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
@Component
public class ConnectProcessor implements IConnectProcessor {

    @Resource
    private ApplicationContext context;

    @Override
    public Object execute(Object proxy, Method method, Object[] args) throws IOException {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String url = buildUrl(method, args);
        Map<String, String> headerMap = buildHeader(method, args);
        String bodyStr = buildBody(method, args);
        //okhttp连接
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse(requestMapping.mediaType().getValue());
        RequestBody body = RequestBody.create(mediaType, bodyStr);
        Request.Builder builder = new Request.Builder().url(url);
        Set<Map.Entry<String, String>> entries = headerMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder = builder.addHeader(entry.getKey(), entry.getValue());
        }
        Request request = builder.build();
        Response response = client.newCall(request).execute();
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
        List<String> paramList = new ArrayList<>();
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
                if (annotation instanceof Query) {
                    Query query = (Query) annotation;
                    paramList.add(query.value() + "=" + arg.toString());
                }
            }
        }
        if (!paramList.isEmpty()) {
            String paramsStr = StringUtils.join(paramList, "&");
            url += ("?" + paramsStr);
        }

        return url;
    }

    private Map<String, String> buildHeader(Method method, Object[] args) {
        return new HashMap<>();
    }

    private String buildBody(Method method, Object[] args) {
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
            bodyStr = arg.toString();
            break;
        }
        return bodyStr;
    }

}
