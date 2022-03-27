package com.github.chyern.connect.processor;

import com.github.chyern.common.enums.ErrorEnum;
import com.github.chyern.common.utils.AssertUtil;
import com.google.gson.GsonBuilder;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
public class DefaultConnectProcessor extends AbstractConnectProcessor {

    @Override
    protected ResponseEntity around() {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30 * 1000);
        factory.setReadTimeout(30 * 1000);
        RestTemplate restTemplate = new RestTemplate(factory);
        HttpEntity<String> requestEntity = new HttpEntity<>(new GsonBuilder().create().toJson(body), httpHeaders);
        return restTemplate.exchange(url, HttpMethod.resolve(method), requestEntity, respClazz);
    }

    @Override
    protected <T> T after(Object obj) {
        ResponseEntity entity = (ResponseEntity) obj;
        AssertUtil.isTrue(HttpStatus.OK == entity.getStatusCode(), ErrorEnum.CONNECT_RESULT_ERROR);
        return (T) entity.getBody();
    }
}
