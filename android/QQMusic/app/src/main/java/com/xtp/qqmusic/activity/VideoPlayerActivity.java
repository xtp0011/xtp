package com.xtp.qqmusic.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.xtp.qqmusic.R;
import com.xtp.qqmusic.bean.VideoItem;
import com.xtp.qqmusic.utils.StringUtil;
import com.xtp.qqmusic.view.VideoView;

import java.util.ArrayList;


/**
 * 播放视频界面
 */
public class VideoPlayerActivity extends BaseActivity{

    private static final int msg_get_system_time = 0;
    private static final int msg_start_update_play_progress=1;
    private static final int msg_auto_hide_controller = 2 ;

    private VideoView video_player_videoview;
    private TextView video_player_tv_title;
    private  VideoItem videoItem ;
    private  ArrayList<VideoItem> videoItems ;
    private ImageView video_player_iv_pause;
    private ImageView video_player_iv_battery;
    private TextView video_player_tv_system_time;
    private SeekBar video_player_sk_volume;
    private ImageView video_player_iv_mute;
    private AudioManager audioManager;
    private int lastVolume ;
    private int halfScreenWidth;
    private int halfScreenHeight;
    private View video_player_cover;
    private float downY ;
    private float downX;
    private int downVolume ;
    private float downAlapha;
    private TextView video_player_tv_position;
    private SeekBar video_player_sk_position;
    private TextView video_player_tv_duration;
    private int position;
    private ImageView video_player_iv_pre;
    private ImageView video_player_iv_next;
    private int topHeight;
    private int bottomeHeight;
    private LinearLayout video_player_ll_bottom;
    private LinearLayout video_player_ll_top;
    private GestureDetector gestureDetector;
    private boolean isControlShowwing =false;
    private ImageView video_player_iv_fullscreen;
    private Uri outSideUri;
    private LinearLayout video_player_ll_loading;

    //更新系统时间
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case msg_get_system_time:
                    startGetSystemTime();
                    break;
                case msg_start_update_play_progress:
                    startUpdatePlayProgress();
                    break;
                case msg_auto_hide_controller:
                    hideController();
                    break;
            }
        }
    };

    private class VideoPlayerOnPreparedListener implements MediaPlayer.OnPreparedListener {
        /**
         * 准备号之后的回调函数
         * @param mp
         */
        @Override
        public void onPrepared(MediaPlayer mp) {
            //开始播放
            video_player_videoview.start();
            //设置视频的标题
            if(outSideUri==null){
                video_player_tv_title.setText(videoItem.getTitle());
            }else {
                video_player_tv_title.setText(outSideUri.toString());
                video_player_ll_loading.setVisibility(View.GONE);
            }
            //更新图片
            switchPlayAndPausePic();
            //更新播放进度
            startUpdatePlayProgress();
        }
    }

    private class MyBatteryChangeBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            //根据电量等级显示图片
            if(level<10){
                video_player_iv_battery.setImageResource(R.drawable.ic_battery_0);
            }else if(level<20){
                video_player_iv_battery.setImageResource(R.drawable.ic_battery_10);
            }else if(level<40){
                video_player_iv_battery.setImageResource(R.drawable.ic_battery_20);
            }else if(level<60){
                video_player_iv_battery.setImageResource(R.drawable.ic_battery_40);
            }else if(level<80){
                video_player_iv_battery.setImageResource(R.drawable.ic_battery_60);
            }else if(level<100){
                video_player_iv_battery.setImageResource(R.drawable.ic_battery_80);
            }else{
                video_player_iv_battery.setImageResource(R.drawable.ic_battery_100);
            }
        }
    }


    /**
     * 改变seekbar
     */
    private class  MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        /**
         * 当前进度改变，
         * @param seekBar  当前的进度
         * @param progress  当前改变后的进度
         * @param fromUser 是否来自于用户 ：通过用户或代码改变seekBar;
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(seekBar.getId()==R.id.video_player_sk_volume){
                //改变音量
                //flags 0 不显示，1显示系统调整音量
                setSystemCurrentVolume(progress);
            }else if(seekBar.getId()==R.id.video_player_sk_position){
                if(fromUser){//如果时用户才改变进度
                    //改变播放进度
                    video_player_videoview.seekTo(progress);
                }
            }
        }

        /**
         *开始拖拽
         * @param seekBar
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        /**
         *停止拖拽
         * @param seekBar
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    /**
     * 播放完成监听
     */
    private class  MyOnCompletionListener  implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            //更新总时长
            video_player_tv_position.setText(StringUtil.formatTime(video_player_videoview.getDuration()));
            video_player_sk_position.setProgress(video_player_videoview.getDuration());
            //移除消息
            handler.removeMessages(msg_start_update_play_progress);
            video_player_iv_pause.setImageResource(R.drawable.video_play_selector);
        }
    }

    /**
     * 缓冲监听
     */
    private class MyOnBufferingUpdateListener implements MediaPlayer.OnBufferingUpdateListener{

        /**
         * 缓冲更新回调
         * @param mp 内部的MediaPlayer
         * @param percent 缓冲进度 0~100
         */
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            float secondaryProgress=percent/100f*video_player_sk_position.getMax();
            video_player_sk_position.setSecondaryProgress((int) secondaryProgress);
        }
    }

    /**
     * 错误提示
     */
    private class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
            builder.setTitle("错误提示").setMessage("亲，无法此视频").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    VideoPlayerActivity.this.finish();
                }
            });
            builder.show();
            return false;
        }
    }

    private void setSystemCurrentVolume(int progress) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.video_player;
    }

    @Override
    public void initView() {
        video_player_videoview = findViewById(R.id.video_player_videoview);
        video_player_tv_title = findViewById(R.id.video_player_tv_title);
        //播放暂停按钮
        video_player_iv_pause = findViewById(R.id.video_player_iv_pause);
        video_player_iv_battery = findViewById(R.id.video_player_iv_battery);
        video_player_tv_system_time = findViewById(R.id.video_player_tv_system_time);
        video_player_sk_volume = findViewById(R.id.video_player_sk_volume);
        video_player_iv_mute = findViewById(R.id.video_player_iv_mute);
        video_player_cover = findViewById(R.id.video_player_cover);
        video_player_tv_position = findViewById(R.id.video_player_tv_position);
        video_player_sk_position = findViewById(R.id.video_player_sk_position);
        video_player_tv_duration = findViewById(R.id.video_player_tv_duration);
        video_player_iv_pre = findViewById(R.id.video_player_iv_pre);
        video_player_iv_next = findViewById(R.id.video_player_iv_next);
        video_player_ll_bottom = findViewById(R.id.video_player_ll_bottom);
        video_player_ll_top = findViewById(R.id.video_player_ll_top);
        video_player_iv_fullscreen = findViewById(R.id.video_player_iv_fullscreen);
        //加载缓冲布局
        video_player_ll_loading = findViewById(R.id.video_player_ll_loading);
    }

    @Override
    public void initListener() {
        //准备好播放监听
        video_player_videoview.setOnPreparedListener(new VideoPlayerOnPreparedListener());
        //播放完成监听
        video_player_videoview.setOnCompletionListener(new MyOnCompletionListener());

        MyOnSeekBarChangeListener seekBarChangeListener = new MyOnSeekBarChangeListener();
        video_player_iv_pause.setOnClickListener(this);
        //注册系统电量改变的广播接收者
        registerBatteryChangeBroadCastReceiver();
        //音量seekbar改变监听
        video_player_sk_volume.setOnSeekBarChangeListener(seekBarChangeListener);
        video_player_iv_mute.setOnClickListener(this);
        //给进度的seekBar设置改变的监听
        video_player_sk_position.setOnSeekBarChangeListener(seekBarChangeListener);
        //设置上一曲/下一曲点击事件
        video_player_iv_pre.setOnClickListener(this);
        video_player_iv_next.setOnClickListener(this);
        //注册手势
        registerDetector();
        //设置全屏监听
        video_player_iv_fullscreen.setOnClickListener(this);
        //缓冲监听
        video_player_videoview.setOnBufferingUpdateListener(new MyOnBufferingUpdateListener());
        //错误监听
        video_player_videoview.setOnErrorListener(new MyOnErrorListener());
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        //是否本地打开
        outSideUri = intent.getData();
        if(outSideUri ==null){
            videoItems = (ArrayList<VideoItem>) intent.getSerializableExtra("videoItems");
            position = intent.getIntExtra("position",0);
            playItem();
            video_player_iv_pre.setEnabled(true);
            video_player_iv_next.setEnabled(true);
        }else {
            video_player_ll_loading.setVisibility(View.VISIBLE);
            video_player_videoview.setVideoURI(outSideUri);
            video_player_iv_pre.setEnabled(false);
            video_player_iv_next.setEnabled(false);
        }

        //获取系统时间
        startGetSystemTime();
        //初始化系统音量
        ininSystemMusicVolume();
        //获取屏幕宽高
        halfScreenWidth= getResources().getDisplayMetrics().widthPixels/2;
        halfScreenHeight = getResources().getDisplayMetrics().heightPixels/2;
        //初始化透明
        ViewHelper.setAlpha(video_player_cover,0);
        //自动隐藏控制条
        initAutoHideControl();
    }


    @Override
    public void onInnerClick(View view) {
        switch (view.getId()){
            case R.id.video_player_iv_pause:
                switchPlayAndPauseAll();
                break;
            case R.id.video_player_iv_mute:
                switchMute();
                break;
            case R.id.video_player_iv_pre:
                playPre();
                break;
            case R.id.video_player_iv_next:
                playNext();
                break;
            case R.id.video_player_iv_fullscreen:
                switchFullScreenAll();
                break;
        }
    }


    /**
     * 切换播放暂停及图片
     */
    private void switchPlayAndPauseAll(){
        switchPlayAndPause();
        switchPlayAndPausePic();
    }

    /**
     * 切换暂停和播放
     */
    private void switchPlayAndPause() {
        if(video_player_videoview.isPlaying()){
            //暂停
            video_player_videoview.pause();
        }else {
            //播放
            video_player_videoview.start();
        }
    }

    /**
     * 切换按钮图片
     */
    private void switchPlayAndPausePic() {
        if(video_player_videoview.isPlaying()){
            video_player_iv_pause.setImageResource(R.drawable.video_pause_selector);
            handler.sendEmptyMessageDelayed(msg_start_update_play_progress,50);
        }else {
            video_player_iv_pause.setImageResource(R.drawable.video_play_selector);
            handler.removeMessages(msg_start_update_play_progress);
        }
    }

    /**
     * 注册系统电量改变的广播接收者
     */
    private void registerBatteryChangeBroadCastReceiver() {
        BroadcastReceiver broadcastReceiver = new MyBatteryChangeBroadCastReceiver();
        //Intent.ACTION_BATTERY_CHANGED
        //系统发送广播，包含系统电量等级level，电池信息，充电状态
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver,intentFilter);
    }


    /**
     * 获取系统时间
     */
    private void startGetSystemTime() {
        long systemTime = System.currentTimeMillis();
        logInfo(String.valueOf(systemTime));
        String time = StringUtil.formatSystemTime();
        video_player_tv_system_time.setText(time);
        handler.sendEmptyMessageDelayed(msg_get_system_time,50);
    }

    /**
     * 销毁activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除所有的消息和回调
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 初始化系统音量
     */
    private void ininSystemMusicVolume() {
        //获取系统服务的音频服务
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        //获取系统当前音量
        int systemCurrentVolume = getSystemCurrentVolume();
        int systemMaxVolume = getSystemMaxVolume();
        //1.初始化SeekBar，当前音量为系统音乐音量
        //2.拖拽SeekBar，调节系统音量
        video_player_sk_volume.setProgress(systemCurrentVolume);
        video_player_sk_volume.setMax(systemMaxVolume);
    }

    private int getSystemMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    private int getSystemCurrentVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 静音处理
     */
    private void switchMute() {
        //获取系统当前音量
        if(getSystemCurrentVolume()!=0){
            lastVolume = getSystemCurrentVolume();
            video_player_sk_volume.setProgress(0);
            setSystemCurrentVolume(0);
        }else {
            setSystemCurrentVolume(lastVolume);
            video_player_sk_volume.setProgress(lastVolume);
        }
    }


    /**
     * 重新Activity的滑动
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //传递事件
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY=event.getY();
                downX = event.getX();
                downVolume = getSystemCurrentVolume();
                downAlapha = ViewHelper.getAlpha(video_player_cover);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                float disY = moveY-downY;
                float disPrecent = disY/halfScreenHeight;
                if(downX<halfScreenWidth){
                    float disVloume = disPrecent*getSystemMaxVolume();
                    int endVolume = (int) (downVolume+disVloume);
                    setSystemCurrentVolume(endVolume);
                    video_player_sk_volume.setProgress(endVolume);
                }else {
                    float disAlapha = disPrecent;
                    float endAlpha = downAlapha+disAlapha;
                    if(endAlpha>=0 && endAlpha<=1){
                        ViewHelper.setAlpha(video_player_cover,endAlpha);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 更新播放进度
     */
    private void startUpdatePlayProgress() {
        //更新当前进度
        //更新总时长
        int currentPosition = video_player_videoview.getCurrentPosition();
        int totalPosition = video_player_videoview.getDuration();
        String sCurrentPosintion = StringUtil.formatTime(currentPosition);
        String sTotalPosition = StringUtil.formatTime(totalPosition);
        video_player_tv_position.setText(sCurrentPosintion);
        video_player_tv_duration.setText(sTotalPosition);
        //sk初始化
        video_player_sk_position.setProgress(currentPosition);
        video_player_sk_position.setMax(totalPosition);
        handler.sendEmptyMessageDelayed(msg_start_update_play_progress,50);
    }

    /**
     * 播放当前条目
     */
    private void playItem() {
        videoItem = videoItems.get(position);
        video_player_videoview.setVideoPath(videoItem.getPath());//设置视频路径后自动准备
    }


    /**
     * 上一曲
     * position --
     */
    private void playPre() {
        if(position==0){
            showToast("亲，已经是第一首了，请不要再点我了");
        }else {
            position--;
            playItem();
        }
    }

    /**
     * 下一曲
     */
    private void playNext() {
        if(position==videoItems.size()-1){
            showToast("亲，已经是最后一首了，请不要再点我了");
        }else {
            position++;
            playItem();
        }
    }

    /**
     * 自动隐藏
     */
    private void initAutoHideControl() {
        //获取控制条自身高度
        //手动测量
        video_player_ll_top.measure(0,0);//随意测量
        //获取测量高度
        topHeight = video_player_ll_top.getMeasuredHeight();

        video_player_ll_bottom.measure(0,0);
        bottomeHeight = video_player_ll_bottom.getMeasuredHeight();

        hideController();

    }

    /**
     * 隐藏控制条
     */
    private void hideController() {
        ViewPropertyAnimator.animate(video_player_ll_top).translationY(-topHeight);
        ViewPropertyAnimator.animate(video_player_ll_bottom).translationY(bottomeHeight);
        isControlShowwing=false;
        handler.removeMessages(msg_auto_hide_controller);
    }

    /**
     * 注册手势，用来控制屏幕
     */
    private void registerDetector () {
        gestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            /**
             * 单击
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                switchControlHideOrShow();
                return super.onSingleTapConfirmed(e);
            }

            /**
             * 长按
             * @param e
             */
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                switchPlayAndPauseAll();
            }

            /**
             * 双击
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                switchFullScreenAll();
                return super.onDoubleTap(e);
            }
        });
    }

    /**
     * 切换控制条显示或隐藏
     */
    private void switchControlHideOrShow() {
        if(!isControlShowwing){
            //显示
            showController();
        }else {
            //隐藏
            hideController();
        }
    }

    /**
     * 显示控制条
     */
    private void showController() {
        ViewPropertyAnimator.animate(video_player_ll_top).translationY(0);//0复原
        ViewPropertyAnimator.animate(video_player_ll_bottom).translationY(0);
        isControlShowwing=true;
        handler.sendEmptyMessageDelayed(msg_auto_hide_controller,2000);
    }

    /**
     * 切换全屏及图片
     */
    private void switchFullScreenAll(){
        switchFullScreen();
        switchFullScreenPic();
    }


    /**
     * 切换全屏
     */
    private void switchFullScreen() {
        video_player_videoview.switchFullScreen();
    }

    /**
     * 切换全屏图片
     */

    private void switchFullScreenPic() {
        if(video_player_videoview.isFullScreen()){
            video_player_iv_fullscreen.setImageResource(R.drawable.btn_default_screen_normal);
        }else {
            video_player_iv_fullscreen.setImageResource(R.drawable.btn_full_screen_normal);
        }
    }


}
