package com.chyern.parent.connect.registered;

import com.chyern.parent.connect.processor.IConnectProcessor;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/23
 */
public class ConnectBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private final Class<? extends IConnectProcessor> processor;

    public ConnectBeanDefinitionScanner(BeanDefinitionRegistry registry, Class<? extends IConnectProcessor> processor) {
        super(registry);
        this.processor = processor;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
        return annotationMetadata.isInterface();
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            constructorArgumentValues.addGenericArgumentValue(beanDefinition.getBeanClassName());
            constructorArgumentValues.addGenericArgumentValue(processor);
            beanDefinition.setBeanClass(ConnectFactoryBean.class);
        }
        return beanDefinitionHolders;
    }
}
