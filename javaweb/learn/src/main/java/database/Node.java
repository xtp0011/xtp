package database;

public class SingleLinked {

    /**
     * 定义节点
     */

    private Node node;

    private class Node{
        private int data;//节点内容
        private Node next;//下一个节点

        public Node(int data){
            this.data=data;
        }
    }


}
