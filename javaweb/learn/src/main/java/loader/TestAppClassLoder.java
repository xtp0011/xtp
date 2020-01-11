package loader;

public class TestAppClassLoder {
    public static void main(String[] args) throws Exception {
        String baseDir = "D:\\test\\";
        MyClassLoader loader = new MyClassLoader(baseDir);
        //此类不要和当前类放在相同目录结构中
        String pkgcls = "loader.Serach";
        Class<?> aClass = loader.loadClass(pkgcls);//不会执行静态代码块
        Object instance = aClass.newInstance();
        System.out.println(instance.getClass());
        System.out.println(instance.getClass().getClassLoader());
    }
}
