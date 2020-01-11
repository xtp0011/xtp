package com.xtp.sourceanalysis.spring.old.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnotationAOPXmlConfig {
    //定义切点，即拦截所有方法名为testAOP的方法的执行
    @Pointcut("execution(* *.testAOP(..))")
    public void targetMethod(){}

    // 切点的方法执行前执行
    @Before("targetMethod()")
    public void beforeAdivce(){
        System.out.println("注解类型前置增强");
    }

    // 切点的方法执行后执行
    @After("targetMethod()")
    public void afterAdivce(){
        System.out.println("注解类型后置增强");
    }

  /*  // 切点的方法返回后执行
    @AfterRuning("targetMethod()")
    public void afterReturningAdivce(){
        System.out.println("方法返回后执行");
    }*/

    // 切点的方法执行抛异常时执行
    @AfterThrowing("targetMethod()")
    public void afterThrowingAdivce(){
        System.out.println("方法执行抛异常");
    }

    // 切点的方法执行前后均执行
    @Around("targetMethod()")
    public void aroundAdvice(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("注解类型环绕增强，方法执行前");

        //执行实际的目标方法
        pjp.proceed();

        System.out.println("注解类型环绕增强，方法执行后");
    }
}
