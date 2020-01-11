package com.xtp.sourceanalysis.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * mybaits中使用的设计模式
 *
 * 1.建造模式
 *     XmlConfigBuilder
 *     XmlStatementBuilder
 *
 * 2.简单工厂
 *      Configuration
 *      DefaultObjectFactory
 *
 * 3.工厂方法
 *      SqlSessionFactory
 *      SqlSessionFactoryBean
 *      DataSourceFactory
 *      TransactionFactory
 *
 *  4.单利模式
 *      ErrorContext(线程内部单利)
 *
 *  5.代理模式
 *        session.getMapper(xxxDao.class)//jdk
 *        对象延迟加载也会产生代理对象
 *
 *  6.装饰模式
 *       new CacheExecutor(executor)
 *
 *  7.适配器模式
 *      Log 接口与日志框架应用
 *
 *  8.责任链模式
 *      Interceptor
 *
 *  9.模板方法模式
 *       BaseExecutor
 *       SqlSessionTemplate
 *
 *  10.策略模式
 *       Cache(FifoCache,LruCache,...)
 *
 *  11.组合模式
 *      SqlNode
 *
 *
 */

public class MainTest {
    public static void main(String[] args) throws IOException {
        String resouce = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resouce);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sessionFactory.openSession();
        UserMapper userMapper  =session.getMapper(UserMapper.class);
        List<User> list = userMapper.selectById(3);
        System.out.println(list);
    }
}
