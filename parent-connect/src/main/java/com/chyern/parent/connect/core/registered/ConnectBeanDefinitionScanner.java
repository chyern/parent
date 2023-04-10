package com.chyern.parent.connect.core.registered;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/23
 */
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
            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();

            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            constructorArgumentValues.addGenericArgumentValue(beanDefinition.getBeanClassName());

            Class<? extends Annotation>[] annotations = (Class<? extends Annotation>[]) annotationAttributes.getClassArray("annotations");
            Set<String> annotationTypes = beanDefinition.getMetadata().getAnnotationTypes();
            for (Class<? extends Annotation> annotation : annotations) {
                String annotationName = annotation.getName();
                if (annotationTypes.contains(annotationName)) {
                    constructorArgumentValues.addGenericArgumentValue(annotation);
                    break;
                }
            }
            beanDefinition.setBeanClass(ConnectFactoryBean.class);
        }
        return beanDefinitionHolders;
    }
}
