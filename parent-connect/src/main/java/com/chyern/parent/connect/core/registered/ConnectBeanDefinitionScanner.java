package com.chyern.parent.connect.core.registered;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/23
 */
@Slf4j
public class ConnectBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private final AnnotationAttributes annotationAttributes;

    public ConnectBeanDefinitionScanner(BeanDefinitionRegistry registry, AnnotationAttributes annotationAttributes) {
        super(registry);
        this.annotationAttributes = annotationAttributes;
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
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(annotationAttributes);
            beanDefinition.setBeanClass(ConnectFactoryBean.class);
        }
        return beanDefinitionHolders;
    }
}
