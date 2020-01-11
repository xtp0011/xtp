package error;

    public class TestException {
        public static void main(String[] args) {
            System.out.println(findResult());
            System.out.println(findResult1());
            System.out.println(findResult2());
        }

        public static boolean findResult(){
            try {
                return true;
            }catch (Exception e){
                return true;
            }finally {
                return false;
            }
        }

        public static Integer findResult1(){
            Integer a = 5;
            try {
                return a = 6;
            }catch (Exception e){
                return a = 7;
            }finally {
                return a = 8;
            }
        }

        public static Integer findResult2(){
            Integer a = 5;
            try {
                return a++;
            }catch (Exception e){
                return a = 7;
            }finally {
                return ++a;
            }
        }
}
