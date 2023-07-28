package com.chenyudan.parent.connect.registered;

import com.chenyudan.parent.connect.ConnectScan;
import com.chenyudan.parent.connect.ConnectScans;
import com.chenyudan.parent.connect.Connect;
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
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> connectScansMap = metadata.getAnnotationAttributes(ConnectScans.class.getName());
        if (connectScansMap != null && connectScansMap.containsKey("value")) {
            AnnotationAttributes[] annotationAttributes = (AnnotationAttributes[]) connectScansMap.get("value");
            for (AnnotationAttributes attributes : annotationAttributes) {
                registerBeanDefinitions(metadata, registry, attributes);
            }
        }

        Map<String, Object> connectScanMap = metadata.getAnnotationAttributes(ConnectScan.class.getName());
        if (connectScanMap != null) {
            AnnotationAttributes attributes = AnnotationAttributes.fromMap(connectScanMap);
            registerBeanDefinitions(metadata, registry, attributes);
        }
    }

    private void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, AnnotationAttributes attributes) {
        String[] basePackages = attributes.getStringArray("value");

        ConnectBeanDefinitionScanner scanner = new ConnectBeanDefinitionScanner(registry);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Connect.class));

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
