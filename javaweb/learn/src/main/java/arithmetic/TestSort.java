package arithmetic;

import java.util.Random;

/**
 * 测试排序
 */

public class TestSort {

    public static final int BEFOR = 0;//排序前
    public static final int AFTER = 1;//排序后
    public static void main(String args[]){

        int[] numbers = creatArray();
        print(numbers,BEFOR);
        //NumberSort.bubbleSort(numbers);//冒泡
        //NumberSort.insertSort(numbers);
        //NumberSort.selectSort(numbers);
        NumberSort.quickSort(numbers,0,numbers.length-1);
        print(numbers,AFTER);
    }

    public static int[] creatArray(){
        int[] numbers = new int[10];
        Random random = new Random();
        for (int i=0;i<numbers.length;i++){
            numbers[i] = random.nextInt(100);
        }
        return numbers;
    }

    public static String arrayToSting(int[] numbers){
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        for (int i=0;i<numbers.length;i++){
            if(i==numbers.length-1){
                builder.append(numbers[i]);
                break;
            }
            builder.append(numbers[i]).append(" ").append(",").append(" ");
        }
        builder.append(" ]");
        return builder.toString();
    }

    public static void print(int[] numbers,int type){
        String array = arrayToSting(numbers);
        String message = "";
        if(type==BEFOR){
            message="排序之前";
        }else if(type==AFTER) {
            message = "排序之后";
        }
        System.out.println(message+" >>>>>>>>:"+array);
    }
}
