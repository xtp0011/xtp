package com.xtp.sourceanalysis.spring.news;

/**
 * spring bean 创建的两种方式
 *      实现BeanFactory接口
 *          create object
 *              创建两个bean实例
 *                  xxxFactoryBean
 *                  xxxFactoryBean对象的getObject方法返回的对象
 *       未实现BeanFactory接口
 *          create object
 *              创建一个bean实例
 *两大bean配置方式
 *      基于xml方式
 *      基于注解方式
 *
 *  spring 中的设计模式
 *
 *  1.简单工厂模式    BeanFactory
 *  2.工厂方法模式    ProxyFactoryBean
 *  3.抽象工厂模式    ClientHttpRequest
 *  4.单利模式        Singleton
 *  5.代理模式        AopProxy
 *  6.策略模式        AOP代理策略，SimpleInstantiationStrategy
 *  7.适配器模式      AdvisorAdapter
 *  8.模板方法模式    JdbcTemplate
 *  9.命令模式        DispatcherServlet
 *
 */
public class SpringIOC {
}
