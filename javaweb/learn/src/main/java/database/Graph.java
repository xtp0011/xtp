package database;

import java.util.Arrays;
import java.util.Stack;

/**
 * 图
 */
public class Graph {

    private Vertex[] vertex ;
    private int currentSize;
    private int[][] adjMat;
    private Stack<Integer> stack;
    private int currentIndex;

    public Graph() {
        this(16);
    }

    public Graph(int size) {
        vertex = new Vertex[size];
        adjMat=new int[size][size];
        stack = new Stack();
    }

    /**
     *向图中加入一个顶点
     * @param v
     */
    public void  addVertex(Vertex v){
        int index = currentSize++;
        vertex[index] = v;
        adjMat[index][index]=1;
    }

    /**
     * 顶点长度
     * @return
     */
    public int size(){
        return currentSize;
    }

    public void addEdge(String v1,String v2){
        //找出两个顶点的下标
        int index1 = 0;
        for (int i=0;i<vertex.length;i++){
            if(vertex[i].getValue().equals(v1)){
                index1=i;
                break;
            }
        }

        int index2 = 0;
        for (int i=0;i<vertex.length;i++){
            if(vertex[i].getValue().equals(v2)){
                index2=i;
                break;
            }
        }
        adjMat[index1][index2]=1;
        adjMat[index2][index1]=1;
    }

    /**
     * 深度优先搜索算法遍历图
     */
    public void dfs(){
        //把第0个顶点标记为已访问状态
        vertex[0].visited=true;
        stack.push(0);
        //打印顶点的值
        System.out.println(vertex[0].getValue());
        out:while (!stack.isEmpty()){
            for (int i=currentIndex+1;i<vertex.length;i++){
                if(adjMat[currentIndex][i]==1&&!vertex[i].visited){
                    //把下一个元素压入栈中
                    stack.push(i);
                    vertex[i].visited=true;
                    System.out.println(vertex[i].getValue());
                    continue out;
                }
            }
            //弹出栈顶元素
            stack.pop();
            //修改当前元素位置为栈顶元素位置
            if(!stack.isEmpty()){
                currentIndex=stack.peek();
            }
        }
    }


    public static void main(String[] args) {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C");
        Vertex v4 = new Vertex("D");
        Vertex v5 = new Vertex("E");
        Graph graph = new Graph(5);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);

        graph.addEdge("A","C");
        graph.addEdge("A","B");
        graph.addEdge("B","C");
        graph.addEdge("B","D");
        graph.addEdge("B","E");

        for (int[] a :graph.adjMat){
            System.out.println(Arrays.toString(a));
        }

        graph.dfs();
    }
}
