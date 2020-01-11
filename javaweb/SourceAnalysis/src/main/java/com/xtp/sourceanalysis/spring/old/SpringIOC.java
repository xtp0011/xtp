package com.xtp.sourceanalysis.spring.old;

/**
 bean的获取
 在BeanFactory的接口继承体系中，主要是提供获取bean，如getBean；列举bean，如ListableBeanFactory；
 提供BeanFactory创建bean需要的组件的ConfigurableBeanFactory；以及对bean注入其他beans的AutowireCapableBeanFactory。

 但是没有提供注册bean的方法声明，即将BeanDefinition注册到BeanFactory实现类内部维护的ConcurrentHashMap类型的map中。

 bean的注册
 1. BeanDefinition的注册
 提供BeanDefinition注册功能的是BeanDefinitionRegistry接口，在这个接口定义注册beanDefinition到BeanFactory的方法声明。

 BeanFactory的实现类会实现BeanDefinitionRegistry，
 并实现BeanDefinitionRegistry接口的registerBeanDefinition系列方法来将给定的BeanDefinition注册到BeanFactory中。

 2. Bean对象实例的注册
 在SingletonBeanRegistry接口的实现类中提供存储的map和注册方法，BeanFactory实现SingletonBeanRegistry接口。


 三. BeanDefinitionRegistry接口

 注册BeanDefinitions。提供registerBeanDefinition，removeBeanDefinition等方法，用来从BeanFactory注册或移除BeanDefinition。

 通常BeanFactory接口的实现类需要实现这个接口。

 实现类（通常为BeanFactory接口实现类）的对象实例，被DefinitionReader接口实现类引用，
 DefinitionReader将BeanDefintion注册到该对象实例中



 @Autowired是Spring自带的，@Inject和@Resource都是JDK提供的，
 其中@Inject是JSR330规范实现的，@Resource是JSR250规范实现的，
 而Spring通过BeanPostProcessor来提供对JDK规范的支持。

 @Autowired、@Inject
 用法基本一样，不同之处为@Autowired有一个required属性，
 表示该注入是否是必须的，即如果为必须的，则如果找不到对应的bean，就无法注入，无法创建当前bean。

 @Autowired、@Inject是默认按照类型匹配的，
 @Resource是按照名称匹配的。如在spring-boot-data项目中自动生成的redisTemplate的bean，
 是需要通过byName来注入的。如果需要注入该默认的，则需要使用@Resource来注入，而不是@Autowired。
 可参看：SpringBoot中注入RedisTemplate实例异常解决

 对于@Autowire和@Inject，如果同一类型存在多个bean实例，则需要指定注入的beanName。
 @Autowired和@Qualifier一起使用，@Inject和@Name一起使用


 BeanFactory在创建一个bean对象实例是，从上到下实现源码如下，其中从最顶层的getBean方法开始，
 调用顺序为：getBean -> doGetBean -> createBean -> doCreateBean：


 对应构造后置处理，是在BeanFactory的initializeBean方法实现的，
 依次调用BeanPostProcessor定义的构造后置处理，InitializingBean的afterPropertiesSet，
 init-method指定的自定义方法；


 对应销毁前置处理，是从destroySingletons方法发起，针对每个bean，
 是在DisposableBeanAdapter的destroy方法中实现的，依次调用BeanPostProcessor的销毁前置处理，
 调用bean的DisposableBean接口实现的destroy方法，调用destroy-method中指定的自定义方法。


 BeanFactory中普通(单例)bean对象的创建：不是FactoryBean创建的bean对象

 注意以下过程是正常bean对象的创建过程，不是由FactoryBean创建bean对象的过程。
 FactoryBean创建bean对象是在自身的getObject方法实现的。

 底层关于bean对象实例创建的核心方法为createBean：
 如下为AbstractAutowireCapableBeanFactory的createBean方法定义：核心过程为首先进行类加载
 ，然后检查是否创建代理对象替代该bean对象，如果是则创建代理对象之间返回，否则进入bean对象的正常创建流程，
 具体在doCreateBean方法定义。


 FactoryBean创建的bean对象
 FactoryBean创建的bean对象，即调用FactoryBean的getObject方法返回的bean对象，
 注册到IOC容器（由FactoryBean创建的bean对象的映射map，即FactoryBeanRegistrySupport的factoryBeanObjectCache），
 也是在doGetBean方法中实现的，具体在FactoryBeanRegistrySupport的getObjectForBeanInstance方法实现。


 一、循环依赖
 spring的循环依赖主要是指两个类相互之间通过@Autowired自动依赖注入对方，
 即类A包含一个类B的对象引用并需要自动注入，类B包含一个类A的对象引用也需要自动注入。

 对于循环依赖问题，spring根据注入方式的不同，采取不同的处理策略，
 对于双方都是使用属性值注入或者setter方法注入，则spring可以自动解决循环依赖注入问题，
 应用程序可以成功启动；对于双方都是使用构造函数注入对方或者主bean对象使用构造函数注入，
 则spring无法解决循环依赖注入，程序报错无法启动。

 二、可以解决的循环依赖
 能够解决循环依赖的情况
 主bean通过属性或者setter方法注入所依赖的bean，所依赖的bean也通过属性或者setter方法注入主bean；
 主bean通过属性或者setter方法注入所依赖的bean，所依赖的bean通过构造函数注入主bean。


 当存在循环依赖时，主bean对象不能通过构造函数的方式注入所依赖的bean对象，
 而所依赖的bean对象则不受限制，即可以通过三种注入方式的任意一种注入主bean对象。
 如果主bean对象通过构造函数方式注入所依赖的bean对象，
 则无论所依赖的bean对象通过何种方式注入主bean，都无法解决循环依赖问题，程序无法启动。

 原因主要是如果主bean对象通过构造函数注入所依赖bean对象时，无法创建该所依赖的bean对象，
 获取该所依赖bean对象的引用。因为如下代码所示，假如在创建主bean对象：

 createBeanInstance是调用构造函数创建主bean对象，在里面会注入构造函数中所依赖的bean，
 而此时并没有执行到addSingletonFactory方法来添加主bean对象的创建工厂到三级缓存singletonFactories中。
 故在createBeanInstance内部，注入和创建该主bean对象在构造函数中所依赖的其他bean对象时，
 假如主bean对象为A，存在对主bean对象依赖的其他bean对象为B，
 则B无法通过三级缓存机制获取主bean对象A的引用（即B如果通过构造函数注入A，则无法创建B对象；
 如果通过属性注入或者setter方法注入A，则创建B对象后，对B对象进行属性赋值，
 会卡在populateBean方法也无法返回），故无法创建主bean对象所依赖的B，创建主bean对象A时，
 createBeanInstance方法无法返回，出现代码死锁，程序报循环依赖错误。

 */
public class SpringIOC {
}
