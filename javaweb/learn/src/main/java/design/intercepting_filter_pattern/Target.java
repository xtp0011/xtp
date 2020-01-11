package design.intercepting_filter_pattern;

/**
 * 创建 Target。
 */
public class Target {
    public void execute(String request){
        System.out.println("Executing request: " + request);
    }
}
