package com.xtp.sourceanalysis.arithmetic;

public class TestMain {
    public static void main(String[] args){
        int[] array = Util.randomArray(10);
        //Sort.bubbleSort(array);
        //Sort.quickSort(array,0,array.length-1);
       // Sort.insertSort(array);
        //Sort.shellSort(array);
        Sort.mergeSort(array,0,array.length-1);
        Util.print(array);
    }
}
