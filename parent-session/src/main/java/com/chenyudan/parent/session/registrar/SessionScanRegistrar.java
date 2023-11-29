package com.chenyudan.parent.session.registrar;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Description: TODO
 *
 * @author chenyu
 * @since 2021/9/27
 */
public class SessionScanRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String SCAN_PACKAGE = "com.chenyudan.session";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.scan(SCAN_PACKAGE);
    }
}
