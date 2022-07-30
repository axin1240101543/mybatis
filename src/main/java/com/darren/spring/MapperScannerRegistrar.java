package com.darren.spring;

import com.darren.annotations.MapperScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 将扫描器和相关工厂类注册到spring容器中
 */
public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata
            , @NonNull BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annotationAttrs = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(MapperScan.class.getName()));
        if (annotationAttrs == null) {
//            log.warn("MapperScan not exist");
            return;
        }
        //构造扫描器，并将spring的beanDefinitionRegistry注入到扫描器内，方便将扫描出的BeanDefinition注入进入beanDefinitionRegistry
        MapperScanner scanner = new MapperScanner(beanDefinitionRegistry);

        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }
        List<String> basePackages = new ArrayList<>();
        for (String pkg : annotationAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        //添加相关过滤器
        scanner.addIncludeFilter(new MapperAnnotationFilter());
        //扫描并注入
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}