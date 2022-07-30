package com.darren.annotations;

import com.darren.spring.MapperScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Mapper扫描器注解
 * 通过@Import的MapperScannerRegistrar实现mapper的扫描和spring容器的定制注入
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(MapperScannerRegistrar.class)
@Target(ElementType.TYPE)
public @interface MapperScan {
    String[] basePackages() default {};
}