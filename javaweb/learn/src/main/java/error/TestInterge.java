package error;

public class TestInterge {


    public static void main(String[] args) {
        int j = 0;
        for(int i=0;i<100;i++){
            j=j++;
        }
        System.out.println(j);
    }

   /* public static final int ENDNUMBER = Integer.MAX_VALUE;
    public static final int STARTNUMBER  = ENDNUMBER-2;

    public static void main(String[] args) {
        int count=0;
        for(int i = STARTNUMBER;i<=ENDNUMBER;i++){
            System.out.println(i);
            count++;
        }
        System.out.println(count);//死循环
    }*/



   /* public static void main(String[] args) {
//        Integer one = 128;
//        Integer two = 128;
//        System.out.println(one==two);//false


//        Integer one = 120;
//        Integer two = 120;
//        System.out.println(one==two);//true

//        Integer one = 128;
//        int two = 128;
//        System.out.println(one==two);//true


        Integer one = new Integer(120);
        Integer two = new Integer(120);
        System.out.println(one==two);//false

        Integer total = 0;
        paramCheck(total);
        System.out.println(total);

    }
*/

    public static void paramCheck(Integer total){
        if(total<1){
            total+=1;
        }
    }
}
