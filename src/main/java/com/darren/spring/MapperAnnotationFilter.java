package com.darren.spring;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.lang.NonNull;

/**
 * 只过滤被Mapper注解的类
 */
public class MapperAnnotationFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, @NonNull MetadataReaderFactory metadataReaderFactory) {
        return metadataReader.getAnnotationMetadata()
                .hasAnnotation("com.darren.annotations.Mapper");
    }
}