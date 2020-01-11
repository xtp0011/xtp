package com.xtp.sourceanalysis.reflect;

/**
 * 肯定是你不希望将其合并到应用中的代码，因此代理使得你可以很容易地添加或移除它们。
 */

interface Interface{
    void doSomething();
    void somethingElse(String arg);
}

class RealObject implements Interface{

    @Override
    public void doSomething() {
        System.out.println("doSomething.");
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("somethingElse " + arg);
    }
}


class SimpleProxy implements Interface{
    private Interface proxy;

    public SimpleProxy(Interface proxy){
        this.proxy=proxy;
    }

    @Override
    public void doSomething() {
        System.out.println("SimpleProxy doSomething.");
        proxy.doSomething();
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("SimpleProxy somethingElse " + arg);
        proxy.somethingElse(arg);
    }
}



public class SimpleProxyDemo {
    public static void consumer(Interface iface){
        iface.doSomething();
        iface.somethingElse("bonobo");
    }

    public static void main(String[] args){
        consumer(new RealObject());
        consumer(new SimpleProxy(new RealObject()));
    }
}
