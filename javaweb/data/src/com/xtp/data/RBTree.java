package com.xtp.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 红黑树实现:
 *  *  性质:
 *  *  1.节点要么红，要么黑;
 *  *  2.根是黑色;
 *  *  3.所有叶子都是黑色;(叶子为null节点)
 *  *  4.每个红色节点的两个子节点都是黑色(从每个叶子到根的所有路径上不能有两个连续的红色节点)
 *  *  5.从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点
 */
public class RBTree<T extends Comparable<T>,D> {

    private Node<T,D> root;//根节点

    private static final Boolean RED = false;
    private static final Boolean BLACK = true;

    private class Node<T extends Comparable<T>,D>{
        private Boolean color;//节点颜色
        private T key ; //键值
        private D data;//具体的数据
        private Node<T,D> parent;//父亲
        private Node<T,D> leftChild;//左孩子
        private Node<T,D> rightChild;//右孩子

        public Node(Boolean color, T key, D data, Node<T, D> parent, Node<T, D> leftChild, Node<T, D> rightChild) {
            this.color = color;
            this.key = key;
            this.data = data;
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public Node(Boolean color, T key, D data) {
            this.color = color;
            this.key = key;
            this.data = data;
        }

        public Node(Boolean color) {
            this.color = color;
        }
    }

    /**
     * 获取父亲
     * @param node
     * @return
     */
    private Node<T,D> parentOf(Node<T,D> node){
        if(node!=null){
            return node.parent;
        }
        return null;
    }

    /**
     * 获取颜色
     * @param node
     * @return
     */
    private boolean colorOf(Node<T,D> node){
        if(node!=null){
            return node.color;
        }
        return BLACK;
    }

    private void setParent(Node<T,D> node,Node<T,D> parent){
        if(node!=null){
            node.parent=parent;
        }
    }

    private void setColor(Node<T,D> node,boolean color){
        if(node!=null){
            node.color=color;
        }
    }

    private boolean isRed(Node<T,D> node){
        return (node!=null&&node.color==RED)?true:false;
    }

    private boolean isBlack(Node<T,D> node){
        return !isRed(node);
    }

    private void setRed(Node<T,D> node){
        if(node!=null){
            node.color=RED;
        }
    }

    private void setBlack(Node<T,D> node){
        if(node!=null){
            node.color=BLACK;
        }
    }

    /**
     * 根据键获取值
     * @param key
     * @return
     */
    public D get(T key){
        Node node = get(root,key);
        return node==null?null: (D) node.data;
    }

    private Node<T,D> get(Node<T,D> node, T key) {
        if(node!=null){
            int cmp = key.compareTo(node.key);
            if(cmp<0){
                return get(node.leftChild,key);
            }else if(cmp>0){
                return get(node.rightChild,key);
            }else {
                return node;
            }
        }
        return null;
    }


    /**
     * 寻找后继节点，即大于该节点的最小节点
     * @param node
     * @return
     */
    private Node<T,D> min(Node<T,D> node){
        while (node.leftChild!=null){
            node = node.leftChild;
        }
        return node;
    }


    /**
     * 寻找待删节点的后继节点
     *      * （因为这个节点即将要被删了，所以要选个后继节点补到这个位置来，
     *      *  选择节点的规则：
     *      *  这个规则和普通二叉树是一样的，要么就是找左子树的最大值，要么就是右子树的最小值）
     * @param node
     * @return
     */
    private Node<T,D> successor(Node<T,D> node){
        if(node.rightChild!=null){
            return min(node.rightChild);
        }

        Node<T,D> news = node.parent;
        while (news!=null&&(news.rightChild==node)){
            node=news;
            news=news.parent;
        }
        return news;
    }

    /**
     * 对某个节点进行左旋
     *      * （当前节点就是父亲节点，整体过程就是 父亲下沉，右孩子上升，然后右孩子的左节点变成了原父亲的右节点）
     * @param x
     */
    private void leftRotate(Node<T,D> x){
        //右孩子
        Node<T, D> y = x.rightChild;

        if (y.leftChild != null) {
            //当前节点 变成了 右孩子的左节点的父亲
            y.leftChild.parent = x;
        }
        x.rightChild = y.leftChild;
        y.leftChild = x;
        //当前的父亲变成了右孩子的父亲
        y.parent = x.parent;

        if (x.parent != null) {
            if (x.parent.leftChild == x) {
                x.parent.leftChild = y;
            } else {
                x.parent.rightChild = y;
            }
        } else {
            this.root = y;
        }
        x.parent = y;

    }

    /**
     * 对某个节点进行右旋
     * @param x
     */
    private void rightRotate(Node<T,D> x){
        Node<T, D> y = x.leftChild;

        if (y.rightChild != null) {
            y.rightChild.parent = x;
        }
        y.parent = x.parent;
        x.leftChild = y.rightChild;
        y.rightChild = x;

        if (x.parent != null) {
            if (x.parent.leftChild == x) {
                x.parent.leftChild = y;

            } else {
                x.parent.rightChild = y;
            }

        } else {
            this.root = y;

        }
        x.parent = y;
    }


    /**
     * 插入后的自平衡过程
     * @param node
     */
    private void balanceInsertion(Node<T,D> node){
        Node<T,D> parent,gparent;//父亲与祖父

        //一开始插入的节点首先肯定是红色，假如父亲不为空且父亲也是红色，那就出现了“双红情况”
        //必须做调整，进入循环
        //假如父亲是黑色，没必要调整，为什么？
        // 因为加一个红节点，不会影响这条路径上的黑色节点个数发生变化，所以不会影响红黑树的性质
        while (((parent=parentOf(node))!=null)&&isRed(parent)){

            //拿到父亲的父亲，也就是祖父
            gparent=parentOf(parent);
            if(gparent==null) continue;

            //根据祖父来判断，父亲是祖父的左子树还是右子树，确定了之后的目的是为了拿到叔父
            if(gparent.leftChild==parent){
                //假如父亲是祖父的左孩子，那通过祖父的右孩子指针就能拿到叔父
                Node<T,D> uncle = gparent.rightChild;
                //进一步，再分两种情况，会发生以下两种情况的前提是父亲为红色 ：
                if(isRed(uncle)){
                    //叔父是红色
                    //父亲变黑，叔父变黑，祖父变红 （为何要这三步，这就是规律，用笔和纸画画就知道）
                    setBlack(parent);
                    setBlack(uncle);
                    setBlack(gparent);
                    //回溯，向上，这一步指针赋值，把祖父变成当前节点，继续向上判断，直到终止
                    node=parent;
                    //插入操作，回溯过程进入这个代码if-else分支，最多一次，为何？
                    //因为叔父一开始是红色，那说明祖父必为黑色，那祖父的兄弟也肯定是黑色，因为黑色的兄弟节点，必然同色
                    //进入一次之后，父亲变成了当前节点，原来的祖父变成了父亲，那说明新父亲不会与新叔父同为红色。
                    continue;
                }else {
                    //叔父是黑色，父亲为红
                    if(parent.rightChild==node){
                        //假如当前节点是父亲的右孩子
                        //父亲要左旋
                        leftRotate(parent);
                        Node<T,D> tmp = node;
                        node=parent;
                        parent=tmp;
                    }
                    //因为上面发生了指针调换，这里parent其实已经指当前节点的指针
                    // 所以是当前节点位置上升变黑，父亲下沉还是红色，祖父变红
                    setBlack(parent);
                    setRed(gparent);
                    //祖父右旋
                    //这里为什么祖父还要右旋一次呢？
                    /*
                             黑祖               黑祖
                            /    \             /   \
                         红父    黑叔  -->  红插   黑叔
                            \               /
                            红插          红父
                         可以看到上面，经过了一次旋转之后，平衡性仍然满足，但是颜色就不对了，仍然存在双红情况
                         其实理解了AVL树的平衡调整，这里其实也是一个“双旋转”的过程，只有被删节点与父亲不同侧时才需要双旋
                         继续接着上面的双红情况，因为颜色不对，这时需要变色
                                    黑祖          红祖
                                   /   \          /   \
                                红插   黑叔 --> 黑插  黑叔
                               /                /
                            红父              红父
                          可以看到，变色之后，右边的子树和左边子树的黑色节点个数保持一致，明显和原来不同，原来的图，看左上角，右子树的黑色节点要多一个
                          原来的树肯定是满足红黑树性质的，所以这里只需要做一次简单的右旋，就可以让右子树的黑色节点个数再次比左边多一个，满足了原来的情况
                     */
                  rightRotate(gparent);
                }
            }else {
                //同理，先找到叔父
                Node<T, D> uncle = gparent.leftChild;
                if(isRed(uncle)){
                    //这个过程其实和上面的是 一致的，不需要旋转，只需要重新染色即可
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node=parent;
                    continue;
                }else {
                    if(parent.leftChild == node) {
                        //假如当前插入节点是父亲的左孩子，那就对父亲做右旋
                        //左孩子上升，父亲下沉
                        rightRotate(parent);
                        Node<T, D> temp = node;
                        node = parent;
                        parent = temp;
                    }
                    //同理，祖父左旋
                    setBlack(parent);
                    setRed(gparent);
                    leftRotate(gparent);
                }
            }
        }
        //调整后，假如都调整到根处了，必须要再次检验，让根为黑色，让它继续满足红黑树的性质
        if(root==node){
            setBlack(node);
        }
    }


    /**
     * 红黑树删除后的平衡调整
     *      * （删除操作比较复杂，所以我加了很多过程状态追踪）
     * @param node
     * @param parent
     *
     * 入参只可能是下面这两种情况：
     *      *               1. node=替换节点 parent=替换节点的父亲节点
     *      *               2. node=替换节点的孩子节点 parent=替换节点
     *      *               3. node=替换节点的孩子节点 parent=替换节点的父节点
     *      *
     *      *               无论是上面哪种情况，都满足：node和parent是儿子父亲的关系
     *      *
     *      *               首先要清楚，什么情况下会进入到这个方法？
     *      *               1.待删节点是黑色节点（且待删节点只有一个子树）
     *      *               或者
     *      *               2.替换节点是黑色节点(待删节点的左右子树都不为空)
     *      *
     *      *               下面仔细分析下上面两种情况，为何只有这两种情况下，才有必要进行调整。
     *      *               对于上面的情况1：
     *      *               待删节点只有一个子树，说明只有一条路径，待删节点移除掉之后，只会影响一条路径上的黑色节点个数。
     *      *               回忆下红黑树的性质，同一个节点出发直到叶子，每一个不同的路径下的黑色节点个数必须是相同的。
     *      *               那既然它只有一个子树，说明只有一条路径。假如待删节点是红色，它移除掉之后，不会影响这条路劲下的黑色节点个数，所以不需要调整。
     *      *               反之，假如它是黑色，它一旦被移除，这一条路径上的黑色个数就减少了，这样会导致这一条路径的黑色节点直接减一
     *      *               这样就会导致这一条路径和 其他由树根出发的路径相比，不再相等。
     *      *
     *      *               对于上面的情况2：
     *      *               因为待删节点下，有两个子树，那要让树继续保持二叉树的关系，那待删位置上必须放入一个符合规则的元素。
     *      *               符合什么规则呢？也就是必须比左子树大，比右子树小，所以我们编程上通常选右子树的最小值。
     *      *               待删元素被删，就要选右子树的最小值放到待删元素位置，那替换节点的指针就会丢失，因为做了移动。
     *      *               既然右子树中有个元素被移除了，假如这个被移除指针的替换节点，是红色，不会对整棵树的平衡性有任何影响。
     *      *               但假如这个替换节点是黑色，一旦被移除，会导致右子树的黑色个数减一，不再相等，所以需要调整。
     *      *
     *      *      对于一次调整过程，最多不会超过三次，这是为什么？
     *      *      上述的情况1比较简单，不需要做任何旋转，只需要染色即可。
     *      *      复杂的是情况2。。
     *      *          情况2又可以分为细分两种情况：
     *      *              1.替换节点在新父亲的左侧
     *      *              2.替换节点在新父亲的右侧
     *      *              (只要理解了其中一种情况，另一种其实也清楚了)
     *      *
     *      *
     *      *              下面只选其中一种情况，做总结
     *      *              对于替换节点在新父亲的左侧这种情况，又可以细分成几种情况（下面这几种情况，才是我觉得红黑树中最难理解的部分）：
     *      *                  1.兄弟节点是红色：兄弟变黑，父亲变红，父亲左旋
     *      *                  2.兄弟节点是黑色
     *      *                    2.1.兄弟节点的左右孩子都是黑色：兄弟染红，整体指针（包括替换节点和其parent）向上回溯一步
     *      *                    2.2.兄弟节点的左孩子是红色，右孩子是黑色：兄弟染红，左孩子染黑，兄弟右旋
     *      *                    2.3.兄弟节点的右孩子是红色：父亲的颜色赋值到兄弟,父亲染黑，兄弟的右孩子染黑，父亲左旋
     *
     */
    private void balanceDeletion(Node<T, D> node, Node<T, D> parent) {
        //先看下调整之前的树结构
        System.out.println("先看下调整之前的树结构>>>>>>>>>>>>>>>>>");
        printTreeLevel2();
        Node<T,D> brother;
        while (isBlack(node)&&node!=root){
            //假如替代节点的颜色也为黑，这里为什么要用“也”字，能进这个方法，肯定说明被删节点也是黑色
            //替代节点也是黑色，那肯定要做调整了，不然整条路径，就少了一个黑色节点，最终肯定是不符合红黑树条件的
            if (parent.leftChild == node) {
                //假如替代节点是其新父亲的左节点，那就通过右指针拿到其兄弟节点
                brother = parent.rightChild;
                System.out.println("当前parent：" + parent.key + " brother(兄弟节点):" + brother.key);
                if (isRed(brother)) {
                    //假如兄弟是红色，那么父亲肯定是黑色
                    System.out.println("兄弟当前是红色");
                    System.out.println("进入balanceDeletion的while（情况2-L-a:兄弟是红色）");
                    System.out.println("----父亲染红，brother染黑，父亲左旋，然后continue");
                    //兄弟与父亲调换颜色，父亲做左旋
                    setRed(parent);
                    setBlack(brother);
                    leftRotate(parent);
                    printTreeLevel2();
                    continue;
                }else {
                    if (isBlack(brother.leftChild) && isBlack(brother.rightChild)) {
                        //假如兄弟节点没有任何孩子节点，也会进入这个代码分支，因为叶子也相当于是黑色的
                        //brother就是替换节点的兄弟节点
                        System.out.println("兄弟节点当前的左右孩子都是黑色");
                        System.out.println("进入balanceDeletion的while（情况2-L-b:兄弟的左右孩子都是黑色）");
                        System.out.println("----brother染红，父亲指针向上回溯");
                        //brother染红，父亲指针向上回溯
                        setRed(brother);
                        node = parent;
                        parent = parentOf(node);
                        printTreeLevel2();
                    }else if (isRed(brother.leftChild) && isBlack(brother.rightChild)) {
                        System.out.println("brother当前的左孩子是红色，右孩子是黑色");
                        System.out.println("进入balanceDeletion的while（情况2-L-c:兄弟的左孩子是红色，右孩子是黑色）");
                        System.out.println("----brother染红，brother的左节点染黑，other做右旋");

                        setRed(brother);
                        setBlack(brother.leftChild);
                        rightRotate(brother);
                        printTreeLevel2();
                    }else if (isRed(brother.rightChild)) {
                        System.out.println("brother右孩子是红色");
                        System.out.println("进入balanceDeletion的while（情况2-L-d:兄弟的右孩子是红色）");
                        System.out.println("----父亲的颜色赋值到brother,父亲染黑，other的右孩子染黑，父亲左旋，跳出while循环");
                        setColor(brother, colorOf(parent));
                        setBlack(parent);
                        setBlack(brother.rightChild);
                        leftRotate(parent);
                        printTreeLevel2();
                        break;
                    }
                }
            }else {
                brother = parent.leftChild;
                System.out.println("当前parent：" + parent.key + " other:" + brother.key);
                if (isRed(brother)) {
                    System.out.println("brother当前是红色");
                    System.out.println("进入balanceDeletion的while（情况2-R-a:兄弟是红色）----brother染黑，parent变红,parent右旋");
                    setBlack(brother);
                    setRed(parent);
                    rightRotate(parent);
                    this.printTreeLevel2();
                    continue;

                }else {
                    if (isBlack(brother.leftChild) && isBlack(brother.rightChild)) {
                        System.out.println("brother当前的左孩子是黑色，brother的右孩子是黑色");
                        System.out.println("进入balanceDeletion的while（情况2-R-b:兄弟的左右孩子都是黑色）----brother变红，指针回溯");
                        setRed(brother);
                        node = parent;
                        parent = parentOf(node);
                        this.printTreeLevel2();
                    }else if (isRed(brother.rightChild) && isBlack(brother.leftChild)) {
                        System.out.println("brother当前的右孩子是红色，brother的左孩子是黑色");
                        System.out.println("进入balanceDeletion的while（情况2-R-c:兄弟的右孩子是红色，左孩子是黑色）----parent变红，brother的右孩子变黑，然后brother做左旋");
                        setRed(parent);
                        setBlack(brother.rightChild);
                        leftRotate(brother);
                        this.printTreeLevel2();
                    }else if (isRed(brother.leftChild)) {
                        System.out.println("brother的左孩子是红色");
                        System.out.println("进入balanceDeletion的while（情况2-R-d:兄弟的左孩子是红色）----父亲的颜色赋值到brother,父亲染黑，brother的左孩子染黑，父亲右旋，跳出while循环");
                        setColor(brother, colorOf(parent));
                        setBlack(parent);
                        setBlack(brother.leftChild);
                        rightRotate(parent);
                        this.printTreeLevel2();
                        break;
                    }
                }
            }

        }
        //在这里，node其实是即将放入被删位置的替代节点
        //假如node是红色，那同时被删节点是黑色，
        // 那说明，直接把node的颜色由红变黑，就直接满足了，不需要做任何旋转
        if (node != null){
            System.out.println("节点：" + node.key + "染黑");
        }
        setBlack(node);
        this.printTreeLevel2();
        System.out.println("调整完成！！！！！！！");
    }

    //红黑树添加操作
    private void insertNode(T key,D data){
        int cmp;
        Node<T, D> news = this.root;
        Node<T, D> node = null;
        //这个过程和二叉查找树的过程是一样的，从上循环到底，直到找到为止
        while (news!=null){
            node=news;
            cmp = key.compareTo(news.key);
            if(cmp==0){
                return;
            }

            if(cmp<0){
                news=news.leftChild;
            }else {
                news=news.rightChild;
            }
        }

        //生成一个新的节点
        Node snode = new Node(BLACK,key,data);
        //通过上面的比较，已经找到了父亲
        snode.parent=node;
        if(node!=null){
            //再次做比较，决定要把新节点放在父亲的哪一边
            cmp = snode.key.compareTo(node.key);
            if(cmp<0){
                node.leftChild=snode;
            }else {
                node.rightChild=snode;
            }
        }else {
            //假如找到的父亲为空，那说明肯定之前就没有根，是空树
            //把这个新节点作为根
            this.root=snode;
        }
        //根据红黑树的性质，把默认节点设置为红色，向上回溯，更容易列举可能出现的情况，
        // 所以这里新节点都默认设置成红色
        setRed(snode);
        //接下来这个就是最关键的方法 ，插入后的自平衡过程，调整让它保持红黑树的性质
        balanceInsertion(snode);
    }


    public void insert(T key,D data){
        insertNode(key,data);
    }

    public void add(T key, D data) {
        insertNode(key, data);
    }

    /**
     * 删除节点
     * @param node
     */
    private void delete(Node<T,D> node){
        Node<T,D> child,parent,replace;
        boolean color = BLACK;
        //删除从整体上也分两种情况，然后两种情况下再细分

        //假如待删除节点的双节点都不为空，这种情况较复杂
        if (node.leftChild != null && node.rightChild != null) {
            //找到了替换节点，就是要接替待删节点指针的新节点
            replace = successor(node);
            //找到替换节点的父亲节点
            parent = parentOf(replace);
            //因为替换节点已经是右子树中的最小值了，所以只有右孩子
            child = replace.rightChild;
            //在这里为什么要获取替换节点的颜色呢？
            //可以这样想，因为替换节点的指针最终肯定是会丢失的，因为替换节点即将接受待删节点的指针，所以替换节点的指针就不再保留了
            //既然不再保留，那说明原来替换节点这里肯定就少了一环，少了一个节点，所以这里就有判断它颜色的必要
            //假如少的恰恰是黑色，那说明它会影响整棵树的平衡性，不再满足红黑树性质
            color = colorOf(replace);
            if (node == parentOf(replace)) {
                //假如替换节点的父亲节点就是当前待删除节点
                //那就直接把待删除节点的指针赋值给parent
                parent = replace;
            }else {
                //假如替换节点的父亲不是待删除节点的父亲
                if (child != null) {
                    //因为替换节点待会是要被删掉的，因为它的值会被放置到待删除节点中，然后把替换节点删除就相当于完成整个删除操作
                    //所以要为替换节点的孩子节点找到新父亲
                    setParent(child, parentOf(replace));
                }
                //然后替换节点的右孩子设置成替换节点的父亲的左孩子
                replace.parent.leftChild = child;
                replace.rightChild = node.rightChild;
                setParent(node.rightChild, replace);
            }
            //把目标删除节点node的父亲设置成替换节点的父亲
            setParent(replace, parentOf(node));
            replace.leftChild = node.leftChild;
            setParent(node.leftChild, replace);
            //除了指针的调整，颜色也要覆盖，替换节点既然来到了待删节点的位置，那么颜色也要沿用之前的颜色，这样才能满足整棵树的性质
            setColor(replace, colorOf(node));
            if (parentOf(node) != null) {
                //待删节点的父亲节点假如不为空，那就要调整父亲节点的左右孩子指针
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = replace;
                } else {
                    node.parent.rightChild = replace;
                }
            }else {
                this.root = replace;
            }
            //上面整个过程就是用replace的指针完全取代了node节点，到此为止，node节点就是一个孤立节点了，就算是删除了
            if (color == BLACK) {
                balanceDeletion(child, parent);
            }
        }else {
            //假如待删节点只有左子树或者右子树
            if (node.leftChild != null) {
                replace = node.leftChild;
            } else {
                replace = node.rightChild;
            }
            //找到待删节点的父亲节点
            parent = parentOf(node);
            if (parent != null) {
                //判断待删节点属于父亲的左还是右
                if (parent.leftChild == node) {
                    parent.leftChild = replace;
                } else {
                    parent.rightChild = replace;
                }
            } else {
                //假如待删节点的父亲为空，那说明它原来就是根
                //它被删了，所以孩子升为根
                this.root = replace;
            }
            //把parent设置成replace的父亲
            setParent(replace, parent);
            color = colorOf(node);
            child = replace;
            //假如待删节点是黑色节点，那说明本次删除肯定会影响红黑树的性质
            //删黑色节点，需要调整平衡，反之，删除红色节点，不需要调整
            if (color == BLACK) {
                balanceDeletion(child, parent);
            }
        }
    }

    public void delete(T key) {
        Node<T, D> node;
        if ((node = get(this.root,key)) != null) {
            delete(node);
        }

    }

    public void remove(T key) {
        Node<T, D> node;
        if ((node = get(this.root,key)) != null) {
            delete(node);
        }
    }

    //前序遍历
    private void preOrder(Node<T, D> node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
    }

    public void preOrder() {
        preOrder(this.root);
    }

    //中序遍历
    private void inOrder(Node<T, D> node) {
        if (node != null) {
            inOrder(node.leftChild);
            System.out.print(node.key + " ");
            inOrder(node.rightChild);
        }
    }

    public void inOrder() {
        inOrder(this.root);
    }

    //后序遍历
    private void postOrder(Node<T, D> node) {
        if (node != null) {
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            System.out.print(node.key + " ");
        }
    }

    public void postOrder() {
        postOrder(this.root);
    }

    /**
     * 打印出整棵树的层级结构，为了方便跟踪旋转的过程
     *
     */
    public void printTreeLevel(){
        System.out.println("开始输出树的层级结构");
        ConcurrentHashMap<Integer, List<Node>> map = showTree();
        int size = map.size();
        for (int i = 0; i < map.size(); i++) {
            System.out.println();
            for (int j = 0; j < map.get(i).size(); j++) {
                System.out.print(makeSpace2(size, i) +
                        (map.get(i).get(j).key == null ? " " : (map.get(i).get(j).key) + (map.get(i).get(j).color? "(黑)":"(红)")) + makeSpace2(size, i));
            }
            System.out.println();
        }
        System.out.println("结束输出树的层级结构");
    }

    private void printTreeLevel2() {
        System.out.println("开始输出树的Graphviz结构");
        ConcurrentHashMap<Integer, List<Node>> map = showTree();
        System.out.println("digraph kunghsu{");
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if(map.get(i).get(j).key != null){
                    System.out.println(map.get(i).get(j).key + " [color="  + (map.get(i).get(j).color == RED?"red":"black")  + " style=filled fontcolor=white] ");
                }
            }
        }
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if(map.get(i).get(j).key != null){
                    if(map.get(i).get(j).leftChild != null){
                        System.out.println(map.get(i).get(j).key + "->" + map.get(i).get(j).leftChild.key + "[label=left]");
                    }
                    if(map.get(i).get(j).rightChild != null){
                        System.out.println(map.get(i).get(j).key + "->" + map.get(i).get(j).rightChild.key + "[label=right]");
                    }
                }
            }
        }
        System.out.println("}");
        System.out.println("结束输出树的Graphviz结构");
    }

    /**
     * 为了让输出更有结构感，在元素前拼接一些空格，对齐
     *
     * @param size
     * @param index
     * @return
     */
    private String makeSpace2(int size, int index){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1 << (size - index); i++) {
            builder.append("  ");
        }
        return builder.toString();
    }

    private ConcurrentHashMap<Integer, List<Node>> showTree(){
        ConcurrentHashMap<Integer, List<Node>> map = new ConcurrentHashMap<>();
        showTree(root, 0, map);
        return  map;
    }

    private void showTree(Node root, int count, ConcurrentHashMap<Integer, List<Node>> map){
        if(map.get(count) == null){
            map.put(count, new ArrayList<>());
        }
        map.get(count).add(root);
        if(root.leftChild != null){
            showTree(root.leftChild, count+1 , map);
        }else{
            //假如为空，也添加到map中，因为我要做格式化控制，空的，我也要知道它这个位置是空的
            if(map.get(count+1) == null){
                map.put(count+1, new ArrayList<>());
            }
            map.get(count+1).add(new Node(RED));
        }
        if(root.rightChild != null){
            showTree(root.rightChild, count+1 , map);
        }else{
            if(map.get(count+1) == null){
                map.put(count+1, new ArrayList<>());
            }
            map.get(count+1).add(new Node(RED));
        }
    }
}
