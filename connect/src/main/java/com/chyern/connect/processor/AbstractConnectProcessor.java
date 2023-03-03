package com.chyern.connect.processor;

import com.chyern.connect.domain.ConnectModel;
import com.chyern.connect.utils.ConnectUtil;
import com.chyern.core.constant.CoreConstant;
import com.chyern.core.utils.AssertUtil;
import com.chyern.spicore.exception.ConnectErrorEnum;
import com.google.gson.GsonBuilder;
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
public abstract class AbstractConnectProcessor implements IConnectProcessor {

    protected ConnectModel connectModel;

    @Override
    public void before(Object proxy, Method method, Object[] args) throws Throwable {
        connectModel = ConnectUtil.buildBy(method, args);
    }

    @Override
    public Object execute(Object proxy, Method method, Object[] args) throws Throwable {
        //okhttp连接
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10L, TimeUnit.SECONDS).callTimeout(30L, TimeUnit.SECONDS).build();

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
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                builder = builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        //调用
        Response response = client.newCall(builder.build()).execute();
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
