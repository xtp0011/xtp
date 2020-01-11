package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        //int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10};
       // int[] arr = new int[]{8,9,6,7,5,4};
        //int[] arr = new int[]{2,1,4,3,5,6};
        int[] arr = new int[]{8,9,5,4,6,7};
        AVLBinaryTree bst = new AVLBinaryTree();
        for(int i : arr){
            bst.add(new AVLBinaryTree.Node(i));
        }

        System.out.println(bst.root.height());
        System.out.println(bst.root.value);

       /* bst.midShow();
        System.out.println();
        System.out.println("==============================");
        bst.delete(5);
        bst.midShow();*/
    }

   /* public static void main(String[] args) {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(3);
        lists.add(2);
        lists.add(1);
        List<Integer> newLists = new ArrayList<>();
        for(Integer i : lists){
            if(!newLists.contains(i)){
                newLists.add(i);
            }
        }
        System.out.println(newLists);

    }*/

    /*public static void main(String[] args) {
       int[] arr = {1,2,3,3,2,3,4,5,4,3,2,1};
       int[] newArr = new int[arr.length];
       int count=0;
       for(int i=0;i<arr.length;i++){
          boolean flag =  true;
          for(int j=0;j<newArr.length;j++){
              if(newArr[j]==arr[i]){
                  flag=false;
              }
           }
          if(flag){
              newArr[count++] = arr[i];
          }
       }
       arr = new int[count];
       for (int i =0;i<arr.length;i++){
           arr[i] = newArr[i];
       }

    }
*/
   /* *//**
     * 测试顺序二次树(完全二叉树)
     *//*
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,0};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        arrayBinaryTree.inOrder();
    }
*/
    /**
     * 测试排序二叉树
     */
  /*  public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.add(7);
        binaryTree.add(6);
        binaryTree.add(5);
        binaryTree.add(1);
        binaryTree.add(2);
        binaryTree.add(3);
        binaryTree.add(4);
        binaryTree.add(10);
        binaryTree.add(9);
        binaryTree.add(8);
        binaryTree.add(0);
        binaryTree.inOrder();
        System.out.println("===================");
        binaryTree.perOrder();
        System.out.println("===================");
        binaryTree.postOrder();
    }*/
}
