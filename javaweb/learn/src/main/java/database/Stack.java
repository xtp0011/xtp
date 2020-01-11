package database;

public class Stack {

    private int[] elements;

    public Stack(){
        elements = new int[0];
    }

    /**
     * 压栈
     * @param element
     */
    public void push(int element){
        int[] newArr = new int[elements.length+1];
        for (int i=0;i<elements.length;i++){
            newArr[i] = elements[i];
        }
        newArr[elements.length] = element;
        elements = newArr;
    }

    /**
     * 出栈
     * @return
     */
    public int pop(){
        if(isEmpty()){
            throw  new RuntimeException("stack is empty");
        }
       int element =  elements[elements.length-1];
       int[] newArr = new int[elements.length-1];
       for (int i=0;i<elements.length-1;i++){
           newArr[i] = elements[i];
       }
       return element;
    }

    /**
     * 查看栈顶元素
     * @return
     */
    public int peek(){
        if(isEmpty()){
            throw  new RuntimeException("stack is empty");
        }
        return elements[elements.length-1];
    }

    /**
     * 检查栈是否为空
     * @return
     */
    public boolean isEmpty(){
        return elements.length==0;
    }
}
