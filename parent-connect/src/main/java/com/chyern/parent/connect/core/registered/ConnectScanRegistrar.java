package com.chyern.parent.connect.core.registered;

import com.chyern.parent.connect.core.ConnectScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/4/22
 */
public class ConnectScanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributeMaps = importingClassMetadata.getAnnotationAttributes(ConnectScan.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationAttributeMaps);
        String[] basePackages = annotationAttributes.getStringArray("basePackages");
        Class<? extends Annotation>[] annotations = (Class<? extends Annotation>[]) annotationAttributes.getClassArray("annotations");

        ConnectBeanDefinitionScanner scanner = new ConnectBeanDefinitionScanner(registry, annotationAttributes);
        for (Class<? extends Annotation> annotation : annotations) {
            scanner.addIncludeFilter(new AnnotationTypeFilter(annotation));
        }

        String[] packagesToScan = getPackagesToScan(importingClassMetadata, basePackages);
        scanner.doScan(packagesToScan);
    }

    private static String[] getPackagesToScan(AnnotationMetadata importingClassMetadata, String[] basePackages) {
        Set<String> packagesToScan = new LinkedHashSet<>(Arrays.asList(basePackages));
        if (packagesToScan.isEmpty()) {
            packagesToScan = Collections.singleton(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return StringUtils.toStringArray(packagesToScan);
    }
}
