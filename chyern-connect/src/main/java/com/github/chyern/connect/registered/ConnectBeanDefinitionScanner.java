package com.github.chyern.connect.registered;

import com.github.chyern.connect.annotation.Connect;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/23
 */
public class ConnectBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public ConnectBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
        return annotationMetadata.isInterface() && annotationMetadata.hasAnnotation(Connect.class.getName());
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClass(ConnectFactoryBean.class);
        }
        return beanDefinitionHolders;
    }
}
