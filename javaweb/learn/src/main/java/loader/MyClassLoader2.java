package loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class MyClassLoader2 extends ClassLoader {
    private String baseDir;
    private Set<String> loadClasses;

    public MyClassLoader2(String baseDir,String[] classes) throws IOException {
        //指定父类加载器为null，打破双亲委派原则
        super(null);
        this.baseDir=baseDir;
        loadClasses=new HashSet<>();
        customerLoadClass(classes);
    }

    private void customerLoadClass(String[] classes) throws IOException {
        for(String classDir : classes){
            loadDirectly(classDir);
            loadClasses.add(classDir);
        }
    }

    private void loadDirectly(String name) throws IOException {
        StringBuilder sb = new StringBuilder(baseDir);
        String classname = name.replace('.', File.separatorChar)+".class";
        sb.append(File.separator).append(classname);
        File file = new File(sb.toString());
        intantaineClass(name,new FileInputStream(file),file.length());
    }

    private void intantaineClass(String name, InputStream is, long length) throws IOException {
        byte[] bytes = new byte[(int) length];
        is.read(bytes);
        is.close();
        //将字节码信息转换为类对象进行存储
        defineClass(name,bytes,0,bytes.length);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Class<?> cls ;
        cls = findLoadedClass(name);
        if(!this.loadClasses.contains(name)&&cls==null)
            cls = getSystemClassLoader().loadClass(name);
        if(cls==null) throw  new ClassNotFoundException(name);
        if(resolve) resolveClass(cls);
        return cls;
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader2 loader = new MyClassLoader2("D:\\test\\",
                new String[]{"loader.Serach"});
        Class<?> loadClass = loader.loadClass("loader.Serach");
        System.out.println(loadClass.getClassLoader());
        Object object = loadClass.newInstance();
        System.out.println(object);
        Thread.sleep(20000);
        loader = new MyClassLoader2("D:\\test\\",
                new String[]{"loader.Serach"});
        loadClass = loader.loadClass("loader.Serach");
        System.out.println(loadClass.getClassLoader());
        object = loadClass.newInstance();
        System.out.println(object);

    }
}
