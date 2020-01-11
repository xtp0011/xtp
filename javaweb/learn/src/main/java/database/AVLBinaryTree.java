package database;

public class AVLBinaryTree {

    /**
     * 定义节点
     */
    static class Node{
        int value;
        Node left;
        Node right;

        public Node(int value){
            this.value=value;
        }

        /**
         * 返回当前节点的高度
         * @return
         */
        public int height(){
            return Math.max(left==null?0:left.height(),right==null?0:right.height())+1;
        }


        /**
         * 获取左子树高度
         * @return
         */
        private int leftHeight(){
            if(left==null){
                return 0;
            }
            return left.height();
        }

        /**
         * 获取右子树高度
         * @return
         */
        private int rightHeight(){
            if(right==null){
                return 0;
            }
            return right.height();
        }


        /**
         * 子树中添加节点
         * @param node
         */
        public void add(Node node) {
            if(node==null){
                return;
            }
            //当前节点比左子树的值更小
            if(node.value<this.value){
                //左子树为空
                if(this.left==null){
                    this.left=node;
                }else{
                    this.left.add(node);
                }
            }else if(node.value>this.value) {
                if (this.right == null) {
                    this.right = node;
                } else {
                    this.right.add(node);
                }
            }else {
                throw new RuntimeException("数据重复异常");
            }

            //检查是否平衡
            //进行左旋转
            if(leftHeight()-rightHeight()>1){
                if(left!=null&&left.leftHeight()<left.rightHeight()){//双旋转
                    //先左旋转，再右旋转
                    left.leftRotate();
                    rightRotate();
                }else {//单旋转
                    rightRotate();
                }
            }else if(leftHeight()-rightHeight()<-1){
                if(right!=null&&right.leftHeight()>right.rightHeight()){//双旋转
                    //先右旋转，再左旋转
                    right.rightRotate();
                    leftRotate();
                }else {//单旋转
                    leftRotate();
                }
            }
        }

        /**
         * 左旋转
         */
        private void leftRotate() {
            Node newNode = new Node(value);
            newNode.left=left;
            newNode.right=right.left;
            value=right.value;
            right=right.right;
            left=newNode;
        }

        /**
         * 右旋转
         */
        private void rightRotate() {
            //创建一个新节点，值等于当前节点的值
            Node newNode = new Node(value);
            //把新节点的右子树设置成当前节点的右子树
            newNode.right=right;
            //把新节点的左子树设置为当前节点的左子树的右子树
            newNode.left=left.right;
            //把当前节点的值设置成左子节点的值
            value=left.value;
            //把当前节点的左子树的设置成左子树的左子树
            left=left.left;
            //把当前节点的右子树设置成新节点
            right=newNode;
        }

        /**
         * 中序遍历二叉树，从小到大
         * @param node
         */
        public void midShow(Node node) {
            if(node==null){
                return;
            }
            midShow(node.left);
            System.out.print(" "+node.value+" ");
            midShow(node.right);
        }

        /**
         * 查找节点
         * @param vaule
         * @return
         */
        public Node search(int vaule) {
            if(this.value==vaule){
                return this;
            }else if(this.value>vaule){
                if(left==null){
                    return null;
                }
                return left.search(vaule);
            }else if(this.value<vaule){
                if(right==null){
                    return null;
                }
                return right.search(vaule);
            }
            return null;
        }

        /**
         * 删除一个节点
         * @param value
         */
        public void delete(int value) {
            //找到该节点
            Node target = search(value);
            if(target==null){//没有父节点
                return;
            }
            //找到父节点
            Node parent = searchParent(value);

            if(target.left==null&&target.right==null){//删除只有叶子节点的情况
                //判断删除左节点还是右节点
                if(parent.left.value==value){
                    parent.left=null;
                }else {
                    parent.right=null;
                }
            }else if(target.left!=null&&target.right!=null){//删除有两个子节点的情况
                //删除又子树中值最小的节点，并获取到该节点的值
                int min = deleteMin(target.right);
                //替换目标节点中的内容
                target.value=min;
            }else {//删除有一个子节点的情况
                if(target.left!=null){
                    if(parent.left.value==value){
                        parent.left=target.left;
                    }else {
                        parent.right=target.left;
                    }
                }else {
                    if(parent.left.value==value){
                        parent.left=target.right;
                    }else {
                        parent.right=target.right;
                    }
                }
            }

        }

        /**
         * 删除一棵树中最小的节点
         * @param node
         * @return
         */
        private int deleteMin(Node node) {
            Node target = node;
            //递归向左找
            while (target.left!=null){
                target=target.left;
            }
            //删除最小的节点
            delete(target.value);
            return target.value;
        }

        /**
         * 搜索父节点
         * @param value
         * @return
         */
        public Node searchParent(int value) {
            if((this.left!=null&&this.left.value==value)||
                    (this.right!=null&&this.right.value==value)){
                return this;
            }else {
                if(this.value>value&&this.left!=null){
                    return this.left.searchParent(value);
                }else if(this.value<value&&this.right!=null){
                    return this.right.searchParent(value);
                }
                return null;
            }
        }
    }

    Node root;

    /**
     * 添加节点
     * @param node
     */
    public void add(Node node){
        if(root==null){
            root=node;
        }else {
           root.add(node);
        }
    }

    /**
     * 中序遍历
     */
    public void midShow(){
        if(root!=null){
            root.midShow(root);
        }
    }

    /**
     * 查找节点
     * @param vaule
     * @return
     */
    public Node search(int vaule){
        if(root==null){
            return null;
        }else {
            return root.search(vaule);
        }
    }

    /**
     * 删除一个节点
     * @param value
     */
    public void delete(int value){
        if(root==null){
            return;
        }else {
            root.delete(value);
        }
    }

}
