package com.xtp.sourceanalysis.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class DynamicProxyHandler implements InvocationHandler {
    private Object proxy;

    public DynamicProxyHandler(Object proxy){
        this.proxy=proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("*** proxy: " + proxy.getClass() +
                ". method: " + method + ". args: " + args);
        if(args!=null){
            for (Object arg : args){
                System.out.println(" " + arg);
            }
        }

        return method.invoke(this.proxy,args);
    }
}

public class DynamicProxyHandlerTest {

    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }

    public static void main(String[] args) {
        RealObject real = new RealObject();
         consumer(real);
         System.out.println("****************************************************");
        // insert a proxy and call again:
        Interface proxy = (Interface)Proxy.newProxyInstance(
        Interface.class.getClassLoader(),
                new Class[]{ Interface.class },
                new DynamicProxyHandler(real));

        consumer(proxy);
    }


}
