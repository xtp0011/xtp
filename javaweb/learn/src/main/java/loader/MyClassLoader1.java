package loader;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader1 extends URLClassLoader {
    public MyClassLoader1(URL[] urls) {
        super(urls,null);//指定父加载器为null
    }

    public static void main(String[] args) throws Exception{
        File file = new File("D:\\test\\");
        URI uri = file.toURI();
        URL[] urls = {uri.toURL()};
        ClassLoader loader = new MyClassLoader1(urls);
        String pkgcls = "loader.Serach";
        Class<?> aClass = loader.loadClass(pkgcls);//不会执行静态代码块
        System.out.println(aClass.getClassLoader());
        Object instance = aClass.newInstance();
        System.out.println(instance);
    }
}
