package design.intercepting_filter_pattern;

/**
 * 创建实体过滤器。
 */
public class DebugFilter implements Filter {
    public void execute(String request){
        System.out.println("request log: " + request);
    }
}
