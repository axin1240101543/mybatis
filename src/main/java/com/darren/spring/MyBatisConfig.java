package com.darren.spring;

import com.darren.spring.MyMapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h3>mybatis</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2022年07月31日 01:17:52
 **/
@Configuration
public class MyBatisConfig {

    private static final String BASE_PACKAGE = "com.darren.dao1";

    @Bean
    public MyMapperScannerConfigurer myMapperScannerConfigurer(){
        MyMapperScannerConfigurer myMapperScannerConfigurer = new MyMapperScannerConfigurer();
        myMapperScannerConfigurer.setBasePackage(BASE_PACKAGE);
        return myMapperScannerConfigurer;
    }

}

