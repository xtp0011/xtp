package database;

/**
 * 线索式二叉树
 */
public class LineBinaryTree {

    private TreeNode root;//根结点

    private TreeNode pre;// 前驱节点

    private class TreeNode{
        int data;//节点权
        TreeNode leftNode;//坐节点
        TreeNode rightNode;//右节点

        //指针标识
        int leftType;
        int rightType;

        public TreeNode(int data){
            this.data=data;
        }
    }

    /**
     * 中序线索二叉树
     */
    public void lineNodes(){
        lineNodes(root);
    }

    public void lineNodes(TreeNode node){
        //当前节点为空，则直接返回
        if(node==null){
            return;
        }

        //处理左子树
        lineNodes(node.leftNode);

        //处理前驱节点
        if(node.leftNode==null){
            //让当前节点的左指针指向前驱节点
            node.leftNode=pre;
            //改变当前节点左指针类型
            node.leftType=1;
        }

        //处理前驱的右指针，如果前驱节点的右指针为null（没有指右下子树）
        if(pre!=null && pre.rightNode==null){
            //让前驱节点的右指针指向当前节点
            pre.rightNode=node;
            //改变前驱节点的右指针类型
            pre.rightType=1;
        }

        pre = node;
        //处理右子树
        lineNodes(node.rightNode);
    }

    public TreeNode getRoot(){
        return root;
    }

    /**
     * 遍历线索二叉树
     */
    public void lineIterate(){
        TreeNode node = root;
        while (node==null){
            //循环找最开始的节点
            while (node.leftType==0){
                node=node.leftNode;
            }
            //打印当前节点的值
            System.out.println(node.data);

            //如果当前节点的右指针指向后继节点，可能后继节点还有后继节点
            while (node.rightType==1){
                node=node.rightNode;
                System.out.println(node.data);
            }
            node=node.rightNode;
        }
    }


}
