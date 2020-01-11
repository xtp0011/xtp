package com.xtp.sourceanalysis.spring.old;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xtp.sourceanalysis.spring.old.service.MessageService;
/**
 
   ApplicationContext 继承了 ListableBeanFactory，这个 Listable 的意思就是，通过这个接口，我们可以获取多个 Bean，最顶层 BeanFactory 接口的方法都是获取单个 Bean 的。
   ApplicationContext 继承了 HierarchicalBeanFactory，Hierarchical 单词本身已经能说明问题了，也就是说我们可以在应用中起多个 BeanFactory，然后可以将各个 BeanFactory 设置为父子关系。
   AutowireCapableBeanFactory 这个名字中的 Autowire 大家都非常熟悉，它就是用来自动装配 Bean 用的，但是仔细看上图，ApplicationContext 并没有继承它，不过不用担心，不使用继承，不代表不可以使用组合，如果你看到 ApplicationContext 接口定义中的最后一个方法 getAutowireCapableBeanFactory() 就知道了。
   ConfigurableListableBeanFactory 也是一个特殊的接口，看图，特殊之处在于它继承了第二层所有的三个接口，而 ApplicationContext 没有。这点之后会用到。


 spring相关配置解析和组件创建其实是在web容器中，启动一个web应用的时候，
 即在其ServletContext组件创建的时候，首先解析web.xml获取该应用配置的listeners列表和servlet列表，
 然后保存在自身的一个属性中，然后通过分发生命周期事件ServletContextEvent给这些listeners，
 从而在listeners感知到应用在启动了，然后自定义自身的处理逻辑，
 如spring的ContextLoaderListener就是解析spring的配置文件并创建相关的bean，
 这样其实也是实现了一种代码的解耦；其次是创建配置的servlet列表，调用servlet的init方法，
 这样servlet可以自定义初始化逻辑，DispatcherServlet就是其中一个servlet。

 所以在ContextLoaderListener和DispatcherServlet的创建时，都会进行WebApplicationContext的创建，
 这里其实就是IOC容器的创建了，即会交给spring-context，spring-beans包相关的类进行处理了，
 故可以从这里作为一个入口，一层一层地剥spring的源码了



 ContextLoader完成spring主容器的创建，工作主要是负责从ServletContext中，
 具体为ServletContext从web.xml文件或者WebApplicationInitializer的实现类中，获取WebApplicationContext的相关配置信息，
 如使用contextClass指定使用哪种WebApplicationContext实现，contextConfigLocation指定spring容器的配置文件在哪里，
 以及获取WebApplicationContextInitializers来在进行spring容器创建之前，对WebApplicationContext进行加工处理。

 而spring容器的创建，则是使用spring-context包的ApplicationContext，spring-beans包的BeanFactory，
 来完成从配置中获取beans定义并创建BeanDefinition，获取一些资源属性值，以及完成单例bean的创建等。
 具体在之后关于ApplicationContext和BeanFactory体系结构的文章中分析。


 * @author 徐太平
 *
 */
public class SpringApplicationContext {
	public static void main (String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
		MessageService service = context.getBean(MessageService.class);
		System.out.println(service.getMessage());
	}
}
