package com.chenyudan.parent.connect.processor.connect.processor;

import com.chenyudan.parent.api.exception.enums.ConnectErrorEnum;
import com.chenyudan.parent.connect.processor.connect.domain.ConnectModel;
import com.chenyudan.parent.connect.processor.connect.utils.ConnectUtil;
import com.chenyudan.parent.core.constant.CoreConstant;
import com.chenyudan.parent.core.utils.AssertUtil;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 22:39
 */
@Slf4j
public abstract class AbstractConnectProcessor {

    protected ConnectModel connectModel;

    private static final OkHttpClient httpClient = new OkHttpClient()
            .newBuilder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .callTimeout(30L, TimeUnit.SECONDS)
            .build();

    public void before(Object proxy, Method method, Object[] args) throws Throwable {
        connectModel = ConnectUtil.buildBy(method, args);
    }

    public Object execute(Object proxy, Method method, Object[] args) throws Throwable {

        //请求体
        RequestBody body = null;
        String bodyStr = connectModel.getBodyStr();
        if (bodyStr != null) {
            body = RequestBody.create(okhttp3.MediaType.parse(connectModel.getMediaType().getValue()), bodyStr);
        }

        //构建请求
        String url = buildUrl(connectModel);
        Request.Builder builder = new Request.Builder().url(url).method(connectModel.getMethod().name(), body);

        //请求头
        Map<String, String> heads = connectModel.getHeads();
        if (heads != null) {
            for (Entry<String, String> entry : heads.entrySet()) {
                builder = builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        //调用
        Response response = httpClient.newCall(builder.build()).execute();
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

    public void after(Object proxy, Method method, Object[] args, Object result) throws Throwable {

    }

    public void throwsException(Object proxy, Method method, Object[] args, Exception exception) {
        log.error("connect throw exception.", exception);
    }

    protected void beforeReturnExecute(Object obj) {

    }

    private String buildUrl(ConnectModel connectModel) {
        String url = buildPathUrl(connectModel.getUrl(), connectModel.getPaths());
        String paramsUrl = connectModel.getParamsUrl();

        if (StringUtils.isNotBlank(paramsUrl)) {
            url = url + CoreConstant.QUESTION_MARK + paramsUrl;
        }
        return url;
    }

    private String buildPathUrl(String methodUrl, Map<String, String> pathsMap) {
        String url = methodUrl;

        if (pathsMap == null) {
            return url;
        }

        for (Entry<String, String> entry : pathsMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            url = url.replaceAll(CoreConstant.OPEN_BRACE + key + CoreConstant.CLOSE_BRACE, value);
        }
        return url;
    }
}
