package com.chyern.parent.connect.registered;

import com.chyern.parent.connect.ConnectScan;
import com.chyern.parent.connect.annotation.Connect;
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
import java.util.stream.Collectors;

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
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ConnectScan.class.getName()));

        String[] value = attributes.getStringArray("value");
        String[] basePackages = attributes.getStringArray("basePackages");
        Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");

        Set<String> packagesToScan = new LinkedHashSet<>();
        packagesToScan.addAll(Arrays.asList(value));
        packagesToScan.addAll(Arrays.asList(basePackages));
        packagesToScan.addAll(Arrays.stream(basePackageClasses).map(ClassUtils::getPackageName).collect(Collectors.toList()));

        if (packagesToScan.isEmpty()) {
            packagesToScan = Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
        }

        return packagesToScan;
    }
}
