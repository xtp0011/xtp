package memory;

import org.mockito.cglib.proxy.Enhancer;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TestCglibProxy {
    public static void main(String[] args) {
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(ProxyObject.class);
            enhancer.setUseCache(false);

            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method,
                                        Object[] args, MethodProxy proxy)
                                        throws Throwable {
                    System.out.println("I am proxy");
                    return proxy.invokeSuper(obj,args);
                }
            });

//            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> {
//                System.out.println("I am proxy");
//                return proxy.invokeSuper(obj, args1);
//            });
            ProxyObject proxyObject = (ProxyObject) enhancer.create();
            proxyObject.test();
        }
    }

    static class ProxyObject{
        public String test(){
          return "thanks for you ";
        }
    }
}
