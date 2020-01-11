package com.xtp.sourceanalysis.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

interface Executor{
    void execute(String statement);
}

class DefaultExecutor implements Executor{

    @Override
    public void execute(String statement) {
        //System.out.println("execute start time:"+System.nanoTime());
        System.out.println("execute "+statement );
        //System.out.println("execute end time:"+System.nanoTime());
    }
}

interface Interceptor{
    Object interceptor(Invocation invocation) throws Exception;
    Object plugin(Object target);
}

class Invocation{
    private Object target;
    private Method method;
    private Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object process() throws Exception {
       return method.invoke(target,args);
    }

}

class LogInterceptor implements Interceptor{

    @Override
    public Object interceptor(Invocation invocation) throws Exception {
        System.out.println("execute start time:"+System.nanoTime());
        Object object = invocation.process();
        System.out.println("execute end time:"+System.nanoTime());
        return object;
    }
    @Override
    public Object plugin(Object target) {
        return TargetProxyFactory.newProxy(target,this);
    }
}

class TransactionInterceptor implements Interceptor{

    @Override
    public Object interceptor(Invocation invocation) throws Exception {
        System.out.println("打开事务...");
        Object object = invocation.process();
        System.out.println("提交事务...");
        return object;
    }

    @Override
    public Object plugin(Object target) {
        return TargetProxyFactory.newProxy(target,this);
    }
}

class InterceptorChain{
    private List<Interceptor> interceptors = new ArrayList<>();
    public void addInterceptor(Interceptor interceptor){
        interceptors.add(interceptor);
    }
    public Object pluginAll(Object target){
        for (Interceptor interceptor:interceptors){
            target=interceptor.plugin(target);
        }
        return target;
    }
}


class TargetProxyHandler implements InvocationHandler{

    private Object target;
    private Interceptor interceptor;

    public TargetProxyHandler(Object target,Interceptor interceptor) {
        this.target = target;
        this.interceptor=interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      /*  System.out.println("execute start time:"+System.nanoTime());
        Object object = method.invoke(target,args);
        System.out.println("execute end time:"+System.nanoTime());*/
        Invocation invocation = new Invocation(target,method,args);
        return interceptor.interceptor(invocation);
    }
}

class TargetProxyFactory{
    public static Object newProxy(Object target,Interceptor interceptor){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new TargetProxyHandler(target,interceptor));
    }
}

public class TestInterceptor {
    public static void main(String[] args){
        Executor target = new DefaultExecutor();
        Interceptor interceptor = new LogInterceptor();
        Interceptor interceptor1 = new TransactionInterceptor();
        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(interceptor);
        interceptorChain.addInterceptor(interceptor1);
        Executor proxy = (Executor) interceptorChain.pluginAll(target);
        proxy.execute("select * form dual");
    }
}
