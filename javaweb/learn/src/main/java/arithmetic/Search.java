public class Search {

    /**
     * 顺序查找：
     *查找算法中顺序查找算是最简单的了，无论是有序的还是无序的都可以，
     * 只需要一个个对比即可，但其实效率很低
     */

    public static int search(int[] numbers,int num){
        for (int i=0;i<numbers.length;i++){
            if(numbers[i]==num){
                return i;
            }
        }
        return -1;
    }

    /**
     * 二分查找 : 前提条件：已排序的数组中查找
     * 二分查找的基本思路是：
     * 1、首先确定该查找区间的中间点位置： int mid = (low+upper) / 2；
     * 2、然后将待查找的值与中间点位置的值比较：
     *     a、若相等，则查找成功并返回此位置。
     *     b、若中间点位置值大于待查值，则新的查找区间是中间点位置的左边区域。
     *     c、若中间点位置值小于待查值，则新的查找区间是中间点位置的右边区域。
     * 3、下一次查找是针对新的查找区间进行的。
     *
     */
    public static int binarySearch(int[] numbers,int num){
        int low =0,upper =numbers.length-1;
        while (low<upper){
            int mid = (upper+low)/2;
            if(numbers[mid]<num){
                low = mid+1;
            }else if(numbers[mid]>num){
                upper=mid-1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 斐波那契查找是根据斐波那契序列的特点对表进行分割
     *
     * @param numbers
     * @param num
     * @return
     */
    public static int fibonacciSearch(int[] numbers,int num){
        int i=0;
        while (getFibonacci(i)-1==numbers.length){
            i++;
        }
        int low = 0;
        int height = numbers.length-1;
        while (low<=height){
            int mid = low+getFibonacci(i-1);
            if(numbers[mid]==num){
                return mid;
            }else if(numbers[mid]>num){
                height=mid-1;
                i--;
            }else if(numbers[mid]<num){
                low=mid+1;
                i-=2;
            }
        }
        return -1;
    }


    /**
     * 得到第num个斐波那契数
     * @param num
     * @return
     */
    public static int getFibonacci(int num){
        int res = 0;
        if(num==0){
            res=0;
        }else if(num==1){
            res=1;
        }else{
            int frist = 0;
            int second = 1 ;
            for(int i =2;i<=num;i++){
                res = frist+second;
                frist=second;
                second=res;
            }
        }
        return res;
    }

}
