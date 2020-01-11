package database;

public class DoubleNode {
    private DoubleNode pre = this;
    private DoubleNode next = this;
    private int data;

    public DoubleNode(int data){
        this.data = data;
    }

    /**
     * 增加节点
     * @param node
     */
    public void after(DoubleNode node){
        DoubleNode next  = this.next();//取出原来的下一个节点
        this.next = node;//把新节点做为当前的下一个节点
        node.pre = this;//把当前的节点作为新节点的上一个节点
        node.next = next;//把原来的节点做为新节点的下一个节点
        next.pre = node;//把原来的下一个节点的上一个节点作为新节点
    }

    /**
     * 下一个节点
     * @return
     */
    public DoubleNode next(){
        return this.next;
    }

    /**
     * 上一个节点
     * @return
     */
    public DoubleNode pre(){
        return this.pre;
    }

    /**
     * 获取数据
     * @return
     */
    public int getData(){
        return this.data;
    }

}
