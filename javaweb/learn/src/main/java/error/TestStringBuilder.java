package error;

import java.util.ArrayList;
import java.util.List;

public class TestStringBuilder {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        Student student = new Student("张三",12);
        list.add(student);
        student = new Student("李四",13);
        list.add(student);
        StringBuilder stringBuilder = new StringBuilder();
        for (Student stu : list){
            stringBuilder.append("学生名称：").append(stu.getName()).append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

}
