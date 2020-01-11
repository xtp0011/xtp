package error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestString {

    /**
     * 由数组转换的list,只能循环遍历，增加元素，删除元素，
     * @param args
     */
    public static void main(String[] args) {
        String[] array = {"张三","李四","王五"};
        List<String> asList = Arrays.asList(array);
        asList.add("孙六");
        //asList.remove("张三");
        System.out.println(asList.size());


    }




    private static final String realName="张三";

    /**
     * 可能出现 NPE
     * @param name
     * @return
     */
    public boolean checkParam1(String name){
        return name.equals(realName);
    }

    /**
     * 优化
     * @param name
     * @return
     */
    public boolean checkParam(String name){
        return realName.equals(name);
    }
/*
    public static void main(String[] args) {
        String test = "";
        List<Student> list = new ArrayList<>();
        Student student = new Student("张三",12);
        list.add(student);
        student = new Student("李四",13);
        list.add(student);
        for (Student stu : list){
            test +="学生名称："+stu.getName();
            test +="\n";
        }
        System.out.println(test);
    }*/

}
