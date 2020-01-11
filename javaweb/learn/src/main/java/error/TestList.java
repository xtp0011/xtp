package error;

import java.util.*;

public class TestList {

    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("this is list");
        Vector<String> vector = new Vector<>();
        vector.add("this is list");
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("this is list");
        System.out.println(arrayList.equals(vector));
        System.out.println(arrayList.equals(linkedList));
    }

   /* public static void main(String[] args) {
        int[] test = new int[]{1,2,3,4};
        List list = Arrays.asList(test);
        System.out.println(list.size());//  1

       *//* Integer[] test = new Integer[]{1,2,3,4};
        List<Integer> list = Arrays.asList(test);
        System.out.println(list.size());//  4
*//*
    }
*/


   /* public static void main(String[] args) {
        List<String> list  =  new ArrayList<>();
        String removename = "李明";
        list.add("李明");
        list.add("张三");
        list.add("李四");
        list.add("王五");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String name = iterator.next();
            if(removename.equals(name)) iterator.remove();
        }
        Iterator<String> iterator1 = list.iterator(); //错误
        while (iterator1.hasNext()){
            String name = iterator1.next();
            if(removename.equals(name)) list.remove(name);
        }
        for(int i = 0;i<list.size();i++){
            String name = list.get(i);
            if(removename.equals(name)) list.remove(name);
        }
        for (String name : list){ //错误
            if(removename.equals(name)) list.remove(name);
        }
        System.out.println(list);
    }*/
}
