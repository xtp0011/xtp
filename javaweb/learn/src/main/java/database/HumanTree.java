package database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *赫夫曼二叉树
 */
public class HumanTree {
    private class Node implements Comparable<Node>{
        int data;
        Node leftNode;
        Node rightNode;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public int compareTo(Node o) {
             return -(this.data-o.data);
        }
    }


    public Node createHumanTree(int[] arr){
        //先使用数组中的元素创建若干个二叉树（只有一个节点）
        List<Node> nodes = new ArrayList<>();
        for(int data : arr){
            nodes.add(new Node(data));
        }
        while (nodes.size()>1){
            //排序
            Collections.sort(nodes);
            //取出权值最小的二叉树
            Node left = nodes.get(nodes.size()-1);
            Node right = nodes.get(nodes.size()-2);
            //创建一个新的二叉树
            Node parent = new Node(left.data+right.data);

            parent.leftNode=left;
            parent.rightNode=right;

            //把取出来的二叉树移除
            nodes.remove(left);
            nodes.remove(right);
            //放入原来的二叉树集合中
            nodes.add(parent);
        }
        return nodes.get(0);
    }


}
