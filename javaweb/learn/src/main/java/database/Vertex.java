package database;

/**
 * 顶点类
 */
public class Vertex {

    private String value;
    public boolean visited ;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Vertex(String value) {
        this.value = value;
    }

    public Vertex() {}

    @Override
    public String toString() {
        return value;
    }
}
