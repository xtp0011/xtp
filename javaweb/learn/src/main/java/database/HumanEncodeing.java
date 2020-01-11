package database;

import java.util.*;

/**
 * 赫夫曼编码
 */
public class HumanEncodeing {

    private static StringBuilder sb =new StringBuilder();

    private static Map<Byte,String> huffCodes  = new HashMap<>();

    private class Node implements Comparable<Node>{
        Byte data;
        int weight;
        Node left;
        Node right;
        public Node(Byte data,int weight){
            this.data=data;
            this.weight=weight;
        }

        @Override
        public int compareTo(Node o) {
            return -(this.weight-o.weight);
        }

        @Override
        public String toString() {
            return "Node[ data=" + data + ", weight=" + weight + "]";
        }
    }

    public byte[] huffmanZip(byte[] bytes){
        //先统计每个byte出现的测试，并放入集合中
        List<Node> nodes = getNodes(bytes);
        //创建一个赫夫曼树
        Node tree = creatHuffmanTree(nodes);
        //创建一个赫夫曼码值表
        Map<Byte,String> huffCodes = getCodes(tree);
        System.out.println("编码前 >>>>>>>>:  "+huffCodes);
        //编码
        byte[] b = zip(bytes,huffCodes);
        return b;
    }


    /**
     * 使用huffman树进行解码
     * @param bytes
     * @return
     */
    public String decode(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            byte b = bytes[i];
            //是否是最后一个
            boolean flag = (i==bytes.length-1);
            sb.append(byteToBitStr(!flag,b));
        }
        System.out.println("解码前 >>>>>>>>："+sb.toString());
        //把字符串按照指定的赫夫曼进行解码
        //把赫夫曼编码的键值对进行调换
        Map<String,Byte> map = new HashMap<>();
        for(Map.Entry<Byte,String> entry : huffCodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }

        //创建一个集合，用于存储byte
        List<Byte> list = new ArrayList<>();
        //处理字符串
        for(int i=0;i<sb.length();){
            int count=1;
            boolean flag = true;
            Byte b = null;
            while (flag){
                String key  = sb.substring(i,i+count);
                 b = map.get(key);
                if(b==null){
                    count++;
                }else {
                    flag=false;
                }
            }
            list.add(b);
            i+=count;
        }
        //把集合转为数组
        byte[] b = new byte[list.size()];
        for(int i=0;i<b.length;i++){
            b[i] = list.get(i);
        }
        return new String(b);
    }

    /**
     * 返回8位的字符串
     * @param b
     * @return
     */
    private String byteToBitStr(boolean flag,byte b){
        int temp = b ;
        if(flag){
            temp|=256;
        }
        String string = Integer.toBinaryString(temp);
        if(flag){
            return string.substring(string.length()-8);
        }else {
            return string;
        }

    }


    /**
     * 进行赫夫曼编码
     * @param bytes
     * @param huffCodes
     * @return
     */
    private byte[] zip(byte[] bytes, Map<Byte, String> huffCodes) {
        StringBuilder sb = new StringBuilder();
        for (Byte b : bytes){
            sb.append(huffCodes.get(b));
        }
        System.out.println("编码后 >>>>>>>>："+sb.toString());
        int len ;
        //定义长度
        if(sb.length()%8==0){
            len = sb.length()/8;
        }else {
            len = sb.length()/8+1;
        }
        //用于存储压缩后的byte
        byte[] newBytes = new byte[len];

        int index = 0;

        for(int i = 0;i<sb.length();i+=8){
            String strByte;
            if(i+8>sb.length()){
                strByte = sb.substring(i);
            }else {
                strByte = sb.substring(i, i + 8);
            }
            byte byt = (byte)Integer.parseInt(strByte,2);
            newBytes[index] = byt;
            index++;
        }
        return newBytes;
    }

    /**
     * 根据赫夫曼获取赫夫曼编码
     * @param tree
     * @return
     */
    private Map<Byte, String> getCodes(Node tree) {
        if(tree==null){
            return null;
        }
        getCodes(tree.left,"0",sb);
        getCodes(tree.right,"1",sb);
        return huffCodes;
    }

    private void getCodes(Node node, String code, StringBuilder sb) {
        StringBuilder newSb = new StringBuilder(sb);
        newSb.append(code);
        if(node.data==null){
            getCodes(node.left,"0",newSb);
            getCodes(node.right,"1",newSb);
        }else {
            huffCodes.put(node.data,newSb.toString());
        }
    }

    /**
     * 创建huffman树
     * @param nodes
     * @return
     */
    private Node creatHuffmanTree(List<Node> nodes) {
        while (nodes.size()>1){
            //排序
            Collections.sort(nodes);
            //取出权值最小的二叉树
            Node left = nodes.get(nodes.size()-1);
            Node right = nodes.get(nodes.size()-2);
            //创建一个新的二叉树
            Node parent = new Node(null,left.weight+right.weight);

            //把之前取出来的二叉树设置为新建的二叉子树
            parent.left = left;
            parent.right=right;

            //把取出来的二叉树移除
            nodes.remove(left);
            nodes.remove(right);
            //放入原来的二叉树集合中
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    /**
     * 把byte数组转为Node集合
     * @param bytes
     * @return
     */
    private List<Node> getNodes(byte[] bytes) {
        List<Node> nodes = new ArrayList<>();
        //存储每个出现的次数
        Map<Byte,Integer> counts = new HashMap<>();
        //统计每个byte出现的次数
        for(Byte b :bytes){
            Integer count = counts.get(b);
            if(count==null){
                counts.put(b,1);
            }else {
                counts.put(b,count+1);
            }
        }
        System.out.println("counts: "+counts);
        //把每个键值对转换一个Node对象
        for (Map.Entry<Byte,Integer> entry : counts.entrySet()){
            nodes.add(new Node(entry.getKey(),entry.getValue()));
        }
        return nodes;
    }

}
