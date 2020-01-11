package com.xtp.data;

/**
 * 红黑树代码实现
 */
public class RBTree_20190804<Key extends Comparable<Key>,Vaule> {

    private static final boolean RED = true;//红色
    private static final boolean BLACK = false;//黑色

    private Node root ; //根节点

    /**
     * 节点
     */
    private class Node{
        private Key key;//键
        private Vaule vaule;//值
        private Node left,right;//左右指针域
        private boolean color;//和父节点连接的颜色
        private int size;//对应子树的节点数

        public Node(Key key, Vaule vaule, boolean color, int size) {
            this.key = key;
            this.vaule = vaule;
            this.color = color;
            this.size = size;
        }
    }

    public RBTree_20190804() {}

    /**
     * 判断叶子节点是否为红色
     * @param node
     * @return
     */
    private boolean isRed(Node node){
        if(node==null) return  false;
        return node.color==RED;
    }

    /**
     * 获取节点数
     * @param node
     * @return
     */
    private int size(Node node){
        if(node==null) return 0;
        return node.size;
    }

    public int size(){
       return size(root);
    }


    public boolean isEmpty(){
        return root==null;
    }

    public Vaule get(Key key){
        if(key==null) throw new IllegalArgumentException("argument to get() is null");
        return get(root,key);
    }

    /**
     * 根据键查找对应的值
     * @param node
     * @param key
     * @return
     */
    private Vaule get(Node node, Key key) {
        while (node!=null){
            if(key.compareTo(node.key)==0){
                return node.vaule;
            }else if (key.compareTo(node.key)<0){
                node = node.left;
            }else {
                node = node.right;
            }
        }
        return null;
    }


    /**
     * 左旋转
     * @param node
     * @return
     */
    private Node rotateLeft(Node node){
        Node news = node.right;
        node.right=news.left;
        news.left=node;
        news.color=node.left.color;
        node.left.color=RED;
        news.size=node.size;
        node.size=size(node.left)+size(node.right)+1;
        return news;
    }

    /**
     * 右旋转
     * @param node
     * @return
     */
    private Node rotateRight(Node node){
        Node news = node.left;
        node.left = news.right;
        news.right=node;
        news.color=news.right.color;
        news.right.color=RED;
        news.size=node.size;
        node.size=size(node.left)+size(node.right)+1;
        return news;
    }

    /**
     * 颜色反转
     * @param node
     */
    private void flipColors(Node node){
        node.color=!node.color;
        node.left.color=!node.left.color;
        node.right.color=!node.right.color;
    }

    /**
     * 插入节点
     * @param key
     * @param vaule
     */
    public void put(Key key,Vaule vaule){
        if(key==null||vaule==null) throw new IllegalArgumentException("argument to put() is null");
        root=put(root,key,vaule);
        root.color=BLACK;
    }

    private Node put(Node node, Key key, Vaule vaule) {
        if(node==null) return new Node(key,vaule,RED,1);
        int cmp = key.compareTo(node.key);
        if(cmp<0){
            node.left=put(node.left,key,vaule);
        }else if(cmp>0){
            node.right=put(node.right,key,vaule);
        }else {
            node.vaule=vaule;
        }

        if(isRed(node.right)&&!isRed(node.left)){
            node=rotateLeft(node);
        }

        if(isRed(node.left)&&isRed(node.left.left)){
            node=rotateRight(node);
        }

        if(isRed(node.left)&&isRed(node.right)){
            flipColors(node);
        }
        node.size=size(node.left)+size(node.right)+1;
        return node;
    }

    /**
     * 返回红黑树的高度
     * @return
     */
    public int getHeight(){
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if(node==null) return 0;
        return Math.max(getHeight(node.left)+1,getHeight(node.right)+1);
    }


}
