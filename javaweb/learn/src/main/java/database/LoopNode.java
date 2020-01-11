package database;

public class LoopNode {

    private int data;//节点内容
    private LoopNode next;//下一个节点

    public LoopNode(int data){
        this.data=data;
    }


    /**
     * 插入节点
     * @param node
     */
    public void after(LoopNode node){
        LoopNode oldNode = this.next();//取出下一个节点
        this.next=node;//把新节点存成当前节点的下一节点
        node.next=oldNode;//把原来的节点设置成新节点的下一个节点
    }

    /**
     * 删除下一个节点
     */
    public void removeNext(){
        LoopNode newNode = this.next().next();
        this.next = newNode;
    }

    /**
     * 获取下一节点
     * @return
     */
    private LoopNode next(){
        return this.next;
    }

    /**
     * 获取节点中的数据
     * @return
     */
    public int getData(){
        return this.data;
    }



}
