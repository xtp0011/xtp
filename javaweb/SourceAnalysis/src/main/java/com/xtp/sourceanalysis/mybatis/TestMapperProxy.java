package com.xtp.sourceanalysis.mybatis;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

interface  SqlSession{
    List<Object> selelctList(String statement);
}

class DefaultSqlSession implements SqlSession{

    @Override
    public List<Object> selelctList(String statement) {
        System.out.println("select list");
        List<Object> list = new ArrayList<>();
        list.add(new User(1,"张三",25,"男"));
        return list;
    }
}

class MapperProxyHandler implements InvocationHandler{

    private SqlSession sqlSession;

    private Class<?> targetInterfance;

    public MapperProxyHandler(SqlSession sqlSession,Class targetInterfance) {
        this.sqlSession = sqlSession;
        this.targetInterfance=targetInterfance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("MapperProxyHandler.invoke()方法执行了....");
        String className = targetInterfance.getName();
        String methodNmae = method.getName();
        String statement = className+"."+methodNmae;
        System.out.println("statement : "+statement);
        return  sqlSession.selelctList(statement);
    }
}




class MapperProxyFactory{
    /**
     * 借助此属性封装要产生代理对象的目标接口
     */
    private Class<?> targetInterfance;


    public MapperProxyFactory(Class<?> targetInterfance) {
        this.targetInterfance = targetInterfance;
    }

    /**
     * 创建代理对象，并且传入一个InvocationHandler对象
     * 在mybatis中，这个InvocationHandler对应的是MapperProxy，
     * 在这里我们定义的类是MapperProxyHandler
     * @return
     */
    public Object newInstance(MapperProxyHandler handler){
        return Proxy.newProxyInstance(targetInterfance.getClassLoader(),//类加载器
                new Class[]{targetInterfance},
                handler);
    }
}

public class TestMapperProxy {
    public static void main(String[] args){
        //1.获取目标接口的类对象
        Class<?> targetInterface = UserMapper.class;
        //2.获取SqlSession对象
        SqlSession sqlSession = new DefaultSqlSession();
        //3.创建一个MapperProxyHandler对象
        MapperProxyHandler proxyHandler = new MapperProxyHandler(sqlSession,targetInterface);
        //4.创建一个MapperProxyFactory工厂对象
        MapperProxyFactory proxyFactory = new MapperProxyFactory(targetInterface);
        //5.基于工厂对象为目标接口创建代理对象
        UserMapper userMapper = (UserMapper) proxyFactory.newInstance(proxyHandler);
        System.out.println(userMapper.getClass().getName());
        //6.基于代理对象执行业务操作
        List<User> list =  userMapper.selectById(1);
        System.out.println(list.get(0));
    }
}
