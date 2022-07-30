package com.darren.spring;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;

import java.io.IOException;
import java.io.InputStream;

/**
 * Mapper的工厂bean
 * 将输入的class类型，代理出实现类
 */
public class MapperFactoryBean<T> implements FactoryBean<T> {
    private SqlSessionFactory sqlSessionFactory;
    private final Class<T> interfaceType;

    public MapperFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() throws Exception {
        this.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.getMapper(interfaceType);
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private void getSqlSessionFactory(){
        // 根据全局配置文件创建出SqlSessionFactory
        // SqlSessionFactory:负责创建SqlSession对象的工厂
        // SqlSession:表示跟数据库建议的一次会话
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
}