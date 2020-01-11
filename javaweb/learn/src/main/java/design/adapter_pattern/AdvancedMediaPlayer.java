package design.adapter_pattern;
/**
 * 为媒体播放器和更高级的媒体播放器创建接口。
 */
public interface AdvancedMediaPlayer {
    void playVlc(String fileName);
    void playMp4(String fileName);
}
