package com.xtp.sourceanalysis.spring.spring.factory;

import com.xtp.sourceanalysis.spring.spring.vo.BeanDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplictionContext {

    //存储bean的实例
    private Map<String,Object> instanceMpa = new HashMap<>();

    //存储配置文件中定义的bean对象的配置信息
    private Map<String,BeanDefinition> beanMap = new HashMap<>();

    public ClassPathXmlApplictionContext(String file) throws Exception {
        //1.读取配置文件
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        //2.解析文件封装数据
        parse(is);

    }

    /**
     * 本次XML解析基于dom实现
     * 市场主流xml解析：dom,dom4j,sax,pull.....
     * @param is
     */
    private void parse(InputStream is) throws Exception {
        //1.创建解析器对象(负责读取xml中的内容)
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //2.解析流对象
        Document doc = builder.parse(is);
        //3.处理document
        processDocument(doc);

    }

    private void processDocument(Document doc) throws Exception {
        //1.获取所有bean元素
        NodeList list = doc.getElementsByTagName("bean");
        //2.迭代bean元素,对其属性进行封装
        for(int i=0;i<list.getLength();i++){
            Node node = list.item(i);
            BeanDefinition bean = new BeanDefinition();
            NamedNodeMap namedNodeMap = node.getAttributes();
            bean.setId(namedNodeMap.getNamedItem("id").getNodeValue());
            bean.setPkgClass(namedNodeMap.getNamedItem("class").getNodeValue());
            bean.setLazy(Boolean.valueOf(namedNodeMap.getNamedItem("lazy").getNodeValue()));
            //存储配置信息
            beanMap.put(bean.getId(),bean);
            //基于配置信息中lazy属性的值，判断是否延时加载
            if(!bean.getLazy()){
               Object object = newBeanInstance(bean.getPkgClass());
               instanceMpa.put(bean.getId(),object);
            }
        }
    }

    /**
     * 基于反射技术创建对象
     * @param pkgClass
     * @return
     * @throws Exception
     */
    private Object newBeanInstance(String pkgClass) throws Exception{
        Class<?> cls = Class.forName(pkgClass);
        Constructor constructor = cls.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    public <T> T getBean(String key,Class<T> t) throws Exception {
        //1.判断当前instanceMap中是否有bean实例
        Object object = instanceMpa.get(key);
        if(object!=null) return (T)object;
        //2.实例中没有对象则创建，并存储到instanceMpa
        object = newBeanInstance(t.getName());
        instanceMpa.put(key,object);
        return (T)object;
    }

    public static void main(String[] args ) throws Exception {
        ClassPathXmlApplictionContext cls = new ClassPathXmlApplictionContext("spring-config.xml");
        //System.out.println(cls.beanMap);
        Object obj = cls.getBean("obj",Object.class);
        Date date = cls.getBean("date",Date.class);
        System.out.println(obj);
        System.out.println(date);
    }
}
