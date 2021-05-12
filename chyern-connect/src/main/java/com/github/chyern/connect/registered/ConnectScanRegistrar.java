package com.github.chyern.connect.registered;

import com.github.chyern.connect.scan.Connect;
import com.github.chyern.connect.scan.ConnectScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
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
        Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);
        ConnectBeanDefinitionScanner scanner = new ConnectBeanDefinitionScanner(registry);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Connect.class));
        scanner.doScan(StringUtils.toStringArray(packagesToScan));
    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(ConnectScan.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
        String[] value = attributes.getStringArray("value");
        Set<String> packagesToScan = new LinkedHashSet<>(Arrays.asList(value));
        packagesToScan.addAll(Arrays.asList(basePackages));
        for (Class<?> basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
        }
        if (packagesToScan.isEmpty()) {
            packagesToScan = Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
        }
        return packagesToScan;
    }
}
