package database;

public class BinaryTreeDemo {

    private class Node{
        int data;
        Node leftChild;
        Node rightChild;
        public Node(int data){
            this.data=data;
        }

        public void disPlay(){
            System.out.print(data+" ");
        }

    }

    private Node root;

    /**
     * 插入数据
     * @param data
     * @return
     */
    public boolean insert(int data){
        Node newNode = new Node(data);
        if(root==null){
            root = newNode;
            return  true;
        }else{
            Node current = root;
            Node parentNode = null;
            while (current!=null){
                parentNode = current;
                if(current.data>data){
                    current = current.leftChild;
                    if(current==null){
                        parentNode.leftChild = newNode;
                        return true;
                    }
                }else {
                    current = current.rightChild;
                    if(current==null){
                        parentNode.rightChild = newNode;
                    }
                }
            }
        }
        return  false;
    }

    /**
     * 中序遍历
     * @param current
     */
    private void inOrderNode(Node current){
        if(current!=null){
            inOrderNode(current.leftChild);
            current.disPlay();
            inOrderNode(current.rightChild);
        }
    }

    public void inOrder(){
        inOrderNode(root);
    }

    /**
     * 前序遍历
     * @param current
     */
    private void perOrderNode(Node current){
        if(current!=null){
            current.disPlay();
            perOrderNode(current.leftChild);
            perOrderNode(current.rightChild);
        }
    }

    public void perOrder(){
        perOrderNode(root);
    }

    /**
     * 后序遍历
     * @param current
     */
    private void postOrderNode(Node current){
        if(current!=null){
            postOrderNode(current.leftChild);
            postOrderNode(current.rightChild);
            current.disPlay();
        }
    }

    public void postOrder(){
        postOrderNode(root);
    }

    /**
     * 查找节点
     * @param key
     * @return
     */
    public Node find(int key){
        Node current = root;
        while (current!=null){
            if(current.data>key){
                current=current.leftChild;
            }else if(current.data<key){
                current=current.rightChild;
            }else{
                return current;
            }
        }
        return null;
    }

    /**
     * 查找最大值
     * @return
     */
    public Node findMax(){
        Node current = root;
        Node maxNode = current;
        while (current!=null){
            maxNode = current;
            current = current.rightChild;
        }
        return maxNode;
    }

    /**
     * 查找最小值
     * @return
     */
    public Node findMin(){
        Node current = root;
        Node minNode = current;
        while (current!=null){
            minNode = current;
            current=current.leftChild;
        }
        return minNode;
    }


    /**
     * 根据key值删除节点
     * @param key
     * @return
     */
    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = false;
        while (current.data != key) {
            parent = current;
            if (current.data > key) {
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }

            if(current==null){
                return false;
            }
        }

        if(current.leftChild==null&&current.rightChild==null){ //如果当前节点没有子节点
            if(current==root){
                root = null;
            }else if(isLeftChild){
                parent.leftChild=null;
            }else {
                parent.rightChild=null;
            }
            return true;
        }else if(current.leftChild==null && current.rightChild!=null){//当前节点有一个子节点，右子节点
            if(current == root){
                root = current.rightChild;
            }else if(isLeftChild){
                parent.leftChild = current.rightChild;
            }else{
                parent.rightChild = current.rightChild;
            }
            return true;
        }else if(current.leftChild!=null&&current.rightChild==null){//当前节点有一个子节点，左子节点
            if(current == root){
                root = current.leftChild;
            }else if(isLeftChild){
                parent.leftChild = current.leftChild;
            }else{
                parent.rightChild = current.leftChild;
            }
            return true;
        }else {//当前节点存在两个子节点
            Node successor = getSuccessor(current);
            if(current==root){
                root =successor;
            }else if(isLeftChild){
                parent.leftChild =successor;
            }else {
                parent.rightChild = successor;
            }
            successor.leftChild = current.leftChild;
        }

        return false;
    }

    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;
        while (current!=null){
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if(successor!=delNode.rightChild){
            successorParent.leftChild =successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

}
