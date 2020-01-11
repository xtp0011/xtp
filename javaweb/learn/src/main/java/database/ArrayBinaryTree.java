package database;

/**
 * 数据形式的二次树
 */
public class ArrayBinaryTree {
    private int[] data;
    public ArrayBinaryTree(int[] data){
        this.data=data;
    }

    public void inOrder(){
        inOrder(0);
    }

    public void inOrder(int index){
        if(data==null||data.length==0){
            return;
        }
        System.out.println(data[index]);

        //2*index+1处理左子树
        if(2*index+1<data.length){
            inOrder(2*index+1);
        }

        //2*index+2处理右子树
        if(2*index+2<data.length){
            inOrder(2*index+2);
        }
    }
}
