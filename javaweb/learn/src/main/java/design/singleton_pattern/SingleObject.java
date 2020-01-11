package com.xtp.design.singleton_pattern;

/**
 * 单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。
 * 这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 *
 * 这种模式涉及到一个单一的类，该类负责创建自己的对象，
 * 同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，
 * 可以直接访问，不需要实例化该类的对象。
 *
 * 1、单例类只能有一个实例。
 * 2、单例类必须自己创建自己的唯一实例。
 * 3、单例类必须给所有其他对象提供这一实例。
 *
 * 何时使用：当您想控制实例数目，节省系统资源的时候。
 *
 * 如何解决：判断系统是否已经有这个单例，如果有则返回，如果没有则创建。
 *
 * 优点：
 *
 * 1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如管理学院首页页面缓存）。
 * 2、避免对资源的多重占用（比如写文件操作）。
 * 缺点：没有接口，不能继承，与单一职责原则冲突，一个类应该只关心内部逻辑，而不关心外面怎么样来实例化。
 *
 * 使用场景：
 *
 * 1、要求生产唯一序列号。
 * 2、WEB 中的计数器，不用每次刷新都在数据库里加一次，用单例先缓存起来。
 * 3、创建的一个对象需要消耗的资源过多，比如 I/O 与数据库的连接等。
 *
 * 注意事项：getInstance() 方法中需要使用同步锁 synchronized (Singleton.class)
 * 防止多线程同时进入造成 instance 被多次实例化。
 *
 *
 */

public class SingleObject {
    //创建 SingleObject 的一个对象
    private static SingleObject instance = new SingleObject();

    //让构造函数为 private，这样该类就不会被实例化
    private SingleObject(){}

    //获取唯一可用的对象
    public static SingleObject getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("Hello World!");
    }
}
