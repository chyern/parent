package com.github.chyern.connect.registered;

import com.github.chyern.common.utils.AssertUtil;
import com.github.chyern.connect.annotation.Connect;
import com.github.chyern.connect.exception.ConnectErrorEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/24
 */
public class ConnectFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware {

    private ApplicationContext context;

    private final Class<T> interfaceType;

    public ConnectFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() throws Exception {
        Connect annotation = interfaceType.getAnnotation(Connect.class);
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        Field memberValuesField = invocationHandler.getClass().getDeclaredField("memberValues");
        memberValuesField.setAccessible(true);
        Map<String, Object> memberValuesValue = (Map<String, Object>) memberValuesField.get(invocationHandler);
        memberValuesValue.put("value", replaceValue(annotation.value()));
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, new ConnectProxy(context));
    }

    private String replaceValue(String value) {
        String newValue = value;
        if (newValue.startsWith("${") && newValue.endsWith("}")) {
            newValue = StringUtils.substringBetween(newValue, "${", "}");
            if (newValue.contains(":")) {
                String substringBefore = StringUtils.substringBefore(newValue, ":");
                String substringAfter = StringUtils.substringAfter(newValue, ":");
                newValue = context.getEnvironment().getProperty(substringBefore, substringAfter);
            } else {
                AssertUtil.isTrue(context.getEnvironment().containsProperty(newValue), ConnectErrorEnum.COULD_NOT_INSTANTIATION_KEY, value);
                newValue = context.getEnvironment().getProperty(newValue);
            }
        }
        return value;
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}