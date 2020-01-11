package database;

public class Node {

    private int data;//节点内容
    private Node next;//下一个节点

    public Node(int data){
        this.data=data;
    }


    /**
     * 为节点追加数据
     * @param node
     */
    public Node append(Node node){
        Node current = this;//当前节点
        while (true){
            Node nextNode = current.next();//取出下一个节点
            if(nextNode==null){
                break;
            }
            current = nextNode;//赋值给当前节点
        }
        current=node;
        return current;
    }

    /**
     * 删除下一个节点
     */
    public void removeNext(){
        Node newNode = this.next().next();
        this.next = newNode;
    }

    /**
     * 获取下一节点
     * @return
     */
    private Node next(){
        return this.next;
    }

    /**
     * 获取节点中的数据
     * @return
     */
    public int getData(){
        return this.data;
    }

    /**
     * 是否最后一个节点
     * @return
     */
    public boolean isLast(){
        return this.next()==null;
    }

    /**
     * 显示所有节点
     */
    public void show(){
        Node currentNode = this;
        while (true){
            System.out.print(currentNode.data+" ");
            currentNode=currentNode.next();
            if(currentNode==null){
                break;
            }
        }
        System.out.println();
    }

    /**
     * 插入节点
     * @param node
     */
    public void after(Node node){
        Node oldNode = this.next();//取出下一个节点
        this.next=node;//把新节点存成当前节点的下一节点
        node.next=oldNode;//把原来的节点设置成新节点的下一个节点
    }

}
