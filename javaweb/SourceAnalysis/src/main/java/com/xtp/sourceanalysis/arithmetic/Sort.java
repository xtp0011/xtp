package com.xtp.sourceanalysis.arithmetic;

public class Sort {


    /**
     * 冒泡排序
     * @param arr
     */
    public static void bubbleSort(int [] arr){
        for(int i=0;i<arr.length-1;i++){
            for(int j =0;j<arr.length-i-1;j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }

    /**
     * 快速排序
     * @param arr
     * @param startIndex 初始下标位置
     * @param endIndex   结束下标位置
     */
    public static void quickSort(int [] arr,int startIndex,int endIndex){
        if(startIndex<endIndex){
            //在数组中找一个标准数字，一般为第0个数字
            int start = arr[startIndex];
            //记录需要排序的下标
            int low = startIndex;
            int high = endIndex;
            //循环找比标准数大的和标准数小的数字
            while (low<high){
                while (low<high&&start<=arr[high]){
                    //右边的数字比标准数小
                    high--;
                }
                //使用右边数字替换左边数字
                arr[low]=arr[high];
                //左边的数字比标准数小
                while (low<high&&arr[low]<=start){
                    low++;
                }
                //使用左边数字替换右边数字
                arr[high]=arr[low];
            }
            // 把标准数赋值给高低重合的数字
            arr[low]=start;
            //处理比标准数小的数字
            quickSort(arr,startIndex,low);
            //处理比标准数大的数字
            quickSort(arr,low+1,endIndex);
        }
    }

    /**
     * 插入排序
     * @param arr
     */
    public static void insertSort(int[] arr){
        //遍历所有数字
        for(int i=1;i<arr.length;i++){
             //如果当前数字比前一个数字小
            if(arr[i]<arr[i-1]){
                int temp = arr[i];
                //遍历当前数字前面的所有数字
                int j;
                for(j=i-1;j>=0&&temp<arr[j];j--){
                    //把前面的数字赋值给后面的数字
                    arr[j+1]=arr[j];
                }
                //把临时变量（外层for循环的当前元素）赋值给不满足条件的后一个元素
                arr[j+1]=temp;
            }
        }
    }

    /**
     * 希尔排序
     * @param arr
     */
    public static void shellSort(int[] arr){
        //遍历所有步长
        for(int d=arr.length/2;d>0;d/=2){
            //遍历所有的元素
            for(int i=d;i<arr.length;i++){
                //遍历本组中所有元素
                for(int j=i-d;j>=0;j-=d){
                    //如果当前元素大于加上步长后的元素
                    if(arr[j]>arr[j+d]){
                        int temp = arr[j];
                        arr[j]=arr[d+j];
                        arr[d+j]=temp;
                    }
                }
            }
        }
    }

    /**
     * 选择排序
     * @param arr
     */
    public static void selectSort(int[] arr){
        //遍历所有数据
        for(int i=0;i<arr.length;i++){
            int minIndex=i;
            //把当前数据与后面所有数据依次比较，并记录下最小的数的下标
            for(int j=i+1;j<arr.length;j++){
                if(arr[minIndex]>arr[j]){
                    minIndex=j;
                }
            }
            //如果最小的数的下标与当前遍历的下标不一致，说名minIndex的数比当前的数更小
            if(i!=minIndex){
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex]=temp;
            }
        }
    }


    /**
     * 归并排序
     * @param arr
     * @param low
     * @param high
     */
    public static void mergeSort(int[] arr,int low,int high){
        int meddle = (low+high)/2;
        if(low<high){
            //处理左边数据
            mergeSort(arr,low,meddle);
            //处理右边数据
            mergeSort(arr,meddle+1,high);
            //归并
            merge(arr,low,meddle,high);
        }
    }

    /**
     * 归并
     * @param arr
     * @param low
     * @param middle
     * @param high
     */
    private static void merge(int[] arr,int low,int middle,int high){
        //用于存储归并后的临时数组
        int[] temp =new int[high-low+1];
        //记录第一个数组的下标
        int i=low;
        //记录第二数组的下标
        int j=middle+1;
        //用于记录在临时数组中的下标
        int index= 0;
        //遍历两个数组，取出小的数字，放入临时文件中
        while (i<=middle&&j<=high){
            if(arr[i]<=arr[j]){//第一个数组的数据更小
                temp[index]=arr[i];
                i++;
            }else {//第二个数组的数据更小
                temp[index]=arr[j];
                j++;
            }
            index++;
        }
        //处理多余的数据
        while (j<=high){
            temp[index]=arr[j];
            j++;
            index++;
        }

        while (i<=middle){
            temp[index]=arr[i];
            i++;
            index++;
        }

        //把临时数据存入新数组
        for(int k=0;k<temp.length;k++){
            arr[k+low]=temp[k];
        }
    }
}
