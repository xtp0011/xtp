package com.xtp.sourceanalysis.spring.old;

/**
1、当request到来时，DispatcherServlet对request进行捕获，
 并执行doService方法，继而执行doDispatch方法。 
2、HandlerMapping解析请求，并且返回HandlerExecutionChain
（其中包含controllers和interceptors），然后通过HandlerExecutionChain得到Handler相关类，
根据Handler获取执行它的HandlerAdapter类。 
3、先执行拦截器的pre相关方法，接着执行handler方法，
它会调用controller的handleRequestInternal方法（方法体由用户自定义），
最后调用拦截器的post相关方法。 
4、解析handler方法返回的ModelAndView（可以在配置文件中配置ResourceViewResolver，也就是视图解析器），
渲染页面并response给客户端。





 DispatcherServlet作为springMVC框架的一个统一前端控制器，需要接收所有发送到这个应用的请求，
 然后在自身启动时，已经加载好的URI和请求处理器映射，获取对应的请求处理器，
 由请求处理器进行实际的请求处理。

 所以基于此，DispatcherServlet需要多种子功能组件来完成请求处理，
 由于应用也可以使用多个DispatcherServlet来接收请求，
 为了对众多子功能组件的封装和多个DispatcherServlet的子组件的隔离性，
 每个DispatcherServlet使用了一个自身独立spring子容器WebApplicationContext来管理自身的子功能组件。
 然后共享同一个root WebApplicationContext（即WEB-INF/applicationContext.xml）来获取公用组件，
 如数据库连接池等。

 在这些子功能组件中，我们需要核心关注HandlerMappings，即请求URI和请求处理器映射这个组件的设计，
 即DispatcherServlet的WebApplicationContext是如何产生这个映射的，映射的设计是怎么样的，
 请求处理器具体是什么，如我们应用代码通常是使用@Controller和@RequestMapping来定义的，
 这些在底层源码是怎么实用的；当一个请求到来时，DispatcherServlet是如何从里面查找的。
 这些问题其实就需要到spring的IOC实现了，即spring-context和spring-beans包的实现，

 * 
 * 
 * @author 徐太平
 *
 */
public class SpringMVC {

}
