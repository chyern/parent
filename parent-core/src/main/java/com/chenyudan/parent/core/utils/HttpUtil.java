package com.chenyudan.parent.core.utils;

import com.chenyudan.parent.api.exception.BaseException;
import com.chenyudan.parent.api.exception.enums.BaseExceptionEnum;
import com.chenyudan.parent.core.constant.Constant;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/5/26 13:19
 */
@Slf4j
public class HttpUtil {

    private static final OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(10L, TimeUnit.SECONDS).callTimeout(30L, TimeUnit.SECONDS).build();


    public static String get(String url, Map<String, Object> params, Map<String, String> headers) {
        String actualUrl = buildUrl(url, params);
        Request.Builder builder = new Request.Builder().url(actualUrl).method("GET", null);
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        return getResult(builder);
    }

    public static String postForJson(String url, Map<String, Object> params, Object body, Map<String, String> headers) {
        String actualUrl = buildUrl(url, params);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), new GsonBuilder().create().toJson(body));
        Request.Builder builder = new Request.Builder().url(actualUrl).method("GET", requestBody);
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        return getResult(builder);
    }

    public static String getResult(Builder builder) {
        try (Response response = httpClient.newCall(builder.build()).execute()) {
            AssertUtil.isTrue(response.isSuccessful(), BaseExceptionEnum.CONNECT_ERROR);
            ResponseBody responseBody = response.body();
            return responseBody != null ? responseBody.string() : null;
        } catch (IOException e) {
            throw new BaseException(BaseExceptionEnum.CONNECT_ERROR);
        }
    }

    /**
     * 构建完整请求地址
     *
     * @param url    地址
     * @param params 参数
     */
    public static String buildUrl(String url, Map<String, Object> params) {
        String actualUrl = url;
        if (params != null && !params.isEmpty()) {
            List<String> paramList = LambdaUtil.mapToList(params, entry -> entry.getKey() + Constant.EQUAL_SIGN + entry.getValue());
            String paramsStr = StringUtil.join(paramList, Constant.AMPERSAND);
            actualUrl = actualUrl + Constant.QUESTION_MARK + paramsStr;
        }
        log.debug("http请求地址:{}", actualUrl);
        return actualUrl;
    }
}
