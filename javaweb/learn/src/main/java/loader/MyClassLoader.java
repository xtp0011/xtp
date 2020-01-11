package loader;

import java.io.*;

/**
 * 自定义类加载器
 *
 */
public class MyClassLoader extends ClassLoader {

    private String baseDir;
    public MyClassLoader(String baseDir){
        this.baseDir=baseDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassBytes(name);
        if(classData==null){
            throw new ClassNotFoundException();
        }else {
            return defineClass(name,classData,0,classData.length);
        }
    }

    /**
     * 自己定义
     * @param name
     * @return
     */
    private byte[] loadClassBytes(String name) {
        String fileName = baseDir+name.replace('.', File.separatorChar)+".class";
        System.out.println("fileName: "+fileName);
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int bufferSize = 1024 ;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length=is.read(buffer))!=-1){
                bos.write(buffer,0,length);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
