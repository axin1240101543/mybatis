package com.darren.spring;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * 自定义@Mapper扫描器
 */
public class MapperScanner extends ClassPathBeanDefinitionScanner {

    public MapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 重写ClassPathBeanDefinitionScanner只扫类逻辑，让接口也能成为spring管理的bean成为可能
     *
     * @param beanDefinition 类定义
     * @return 是否是标准组件
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    /**
     * 重写扫描逻辑 使用原来扫描方法的同时，将扫描出来的bean修改为工厂bean注入到spring中
     *
     * @param basePackages 类路径
     * @return 类定义
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(@NonNull String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (!beanDefinitions.isEmpty()) {
            this.processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    /**
     * 模仿mybatis，但是不用搞的那么复杂，直接将类对象交给工厂，让工厂返回代理后的mapper
     *
     * @param beanDefinitions 类定义
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        String beanClassName = "";
        try {
            for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitions) {
                definition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
                beanClassName = definition.getBeanClassName();
                Class<?> mapperClazz = Class.forName(definition.getBeanClassName());
                definition.setBeanClass(MapperFactoryBean.class);
                //将mapper类型注入到工厂的构造方法中
                definition.getConstructorArgumentValues().addGenericArgumentValue(mapperClazz);
                //只能以类类型注入到spring容器中，也就是说要@Resource进入依赖注入
                definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            }
        } catch (ClassNotFoundException e) {
            logger.error("esMapperScanner inject error| cannot inject class name:"
                    + beanClassName, e);
        }
    }
}