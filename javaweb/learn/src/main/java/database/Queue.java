package database;

public class Queue {

    private int[] elements;

    public Queue(){
        elements = new int[0];
    }

    /**
     * 入队
     * @param element
     */
    public void add(int element){
        int[] newArr = new int[elements.length+1];
        for (int i=0;i<elements.length;i++){
            newArr[i] = elements[i];
        }
        newArr[elements.length] = element;
        elements = newArr;
    }

    /**
     * 出队
     */
    public int poll(){

        if(isEmpty()){
            throw  new RuntimeException("Queue is empty");
        }

        int element = elements[0];
        int[] newArr = new int[elements.length-1];
        for(int i=0;i<newArr.length;i++){
            newArr[0] = elements[i+1];
        }
        elements = newArr;
        return element;
    }

    /**
     * 检查队列是否为空
     * @return
     */
    public  boolean isEmpty(){
        return elements.length==0;
    }
}
