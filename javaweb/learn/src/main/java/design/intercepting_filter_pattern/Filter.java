package design.intercepting_filter_pattern;

/**
 * 创建过滤器接口 Filter。
 */
public interface Filter {
    void execute(String request);
}
