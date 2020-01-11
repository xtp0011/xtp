package error;

public class TestClass {
    public static void main(String[] args) throws ClassNotFoundException {
        Student student = new Student();
        Class studentClass1 = student.getClass();
        Class studentClass2 = Student.class;
        Class studentClass3 = Class.forName("error.Student");
        System.out.println(studentClass1==studentClass2);
        System.out.println(studentClass1==studentClass3);
    }
}
