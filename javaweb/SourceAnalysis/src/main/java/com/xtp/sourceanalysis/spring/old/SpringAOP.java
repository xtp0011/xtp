package com.xtp.sourceanalysis.spring.old;

/**
 *
 SpringAOP设计的主要目的是将多个方法中的公用逻辑抽离出来，
 封装在一个Interctor拦截器中，然后使用该拦截器对这些方法的调用Invocation进行拦截


 AOP配置的核心元素为：pointcut，advisor，aspect，pointcut用于定义需要该辅助功能的类或方法集合
 advisor则是将advice和pointcut结合起来，在spring的IOC容器启动时，
 为pointcut匹配的类生成代理对象，使用拦截器拦截对应的方法的执行，将辅助功能advice添加进去；
 aspect表示一个完整切面，即在aspect对应的类中定义辅助方法advice，
 然后在aspect中组装到pointcut拦截的方法集合中

 */
public class SpringAOP {
}
