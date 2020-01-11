package com.xtp.qqmusic.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.xtp.qqmusic.R;
import com.xtp.qqmusic.bean.AudioItem;
import com.xtp.qqmusic.services.AudioPlayerService;
import com.xtp.qqmusic.utils.StringUtil;

public class AudioPlayerActivity extends BaseActivity{

    private static final int msg_update_play_progress=0;

    private RegisterAudioPreparedBroadCastReceiver audioPreparedBroadCastReceiver;
    private ImageView audio_player_iv_pause;
    private TextView audio_player_tv_title;
    private TextView audio_player_tv_arties;
    private AudioServiceConnection audioServiceConnection;
    private AudioPlayerService.AudioBinder audioBinder;
    private ImageView audio_player_wave;
    private TextView audio_player_tv_position;
    private SeekBar audio_player_sk_position;
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case msg_update_play_progress:
                    startUpdatePlayProgress();
                    break;
            }
        }
    };
    private ImageView audio_player_iv_pre;
    private ImageView audio_player_iv_next;
    private ImageView audio_player_iv_playmode;


    /**
     * 通过收到通知进行修改UI
     */
    private class RegisterAudioPreparedBroadCastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            AudioItem audioItem = (AudioItem) intent.getSerializableExtra("AudioItem");
            //更新界面
            audio_player_iv_pause.setImageResource(R.drawable.audio_pause_selector);
            audio_player_tv_title.setText(audioItem.getTitle());
            audio_player_tv_arties.setText(audioItem.getArtist());
            //开启示波器动画
            startWaveAnimation();
            //开始更新播放进度
            startUpdatePlayProgress();
        }

    }

    /**
     * 绑定service
     */
    private class AudioServiceConnection implements ServiceConnection{

        /**
         * 连接成功
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            audioBinder = (AudioPlayerService.AudioBinder) service;
        }

        /**
         * 断开连接
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    /**
     * 拖动seekbar处理
     */
    private class AudioOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //用户拖拽的时候需要mediaPlayer定位播放
            if(fromUser){
                audioBinder.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.audio_player;
    }

    @Override
    public void initView() {
        audio_player_iv_pause = findViewById(R.id.audio_player_iv_pause);
        audio_player_tv_title = findViewById(R.id.audio_player_tv_title);
        audio_player_tv_arties = findViewById(R.id.audio_player_tv_arties);
        audio_player_wave = findViewById(R.id.audio_player_wave);
        audio_player_tv_position = findViewById(R.id.audio_player_tv_position);
        audio_player_sk_position = findViewById(R.id.audio_player_sk_position);
        audio_player_iv_pre = findViewById(R.id.audio_player_iv_pre);
        audio_player_iv_next = findViewById(R.id.audio_player_iv_next);
        audio_player_iv_playmode = findViewById(R.id.audio_player_iv_playmode);
    }

    @Override
    public void initListener() {
        //注册音乐准备好的广播监听者
        registerAudioPreparedBroadCastReceiver();
        audio_player_iv_pause.setOnClickListener(this);
        audio_player_sk_position.setOnSeekBarChangeListener(new AudioOnSeekBarChangeListener());
        audio_player_iv_pre.setOnClickListener(this);
        audio_player_iv_next.setOnClickListener(this);
        audio_player_iv_playmode.setOnClickListener(this);
    }


    @Override
    public void onInnerClick(View view) {
        switch (view.getId()){
            case R.id.audio_player_iv_pause:
                switchPlayAndPauseAll();
                break;
            case R.id.audio_player_iv_pre:
                playPre();
                break;
            case R.id.audio_player_iv_next:
                playNext();
                break;
            case R.id.audio_player_iv_playmode:
                switchPlayMode();
                break;

        }
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
      /*  try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audioItem.getPath());
            mediaPlayer.prepareAsync();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
      //将播放音乐放入服务中
       // Intent intentService = new Intent(this, AudioPlayerService.class);
        //Intent intentService = intent;
        Intent intentService = new Intent(intent);
        intentService.setClass(this,AudioPlayerService.class);
        startService(intentService);
        audioServiceConnection = new AudioServiceConnection();
        bindService(intentService, audioServiceConnection, Service.BIND_AUTO_CREATE);//Service.BIND_AUTO_CREATE 自动创建服务
    }

    //注册音乐准备好的广播监听者
    private void registerAudioPreparedBroadCastReceiver() {
        audioPreparedBroadCastReceiver = new RegisterAudioPreparedBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AudioPlayerService.Action_Audio_Prepared_BroadCast);
        registerReceiver(audioPreparedBroadCastReceiver, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消通知
        if (audioPreparedBroadCastReceiver!=null){
            unregisterReceiver(audioPreparedBroadCastReceiver);
        }
        //解绑服务
        if(audioServiceConnection!=null){
            unbindService(audioServiceConnection);
        }
        //移除所有消息
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 切换暂停
     */
    private void switchPlayAndPauseAll() {
        audioBinder.switchPlayAndPause();
        switchPlayAndPausePic();
    }

    private void switchPlayAndPausePic() {
        if(audioBinder.isPlaying()){
            audio_player_iv_pause.setImageResource(R.drawable.audio_pause_selector);
            startWaveAnimation();
            //不做延时播放
            handler.sendEmptyMessage(msg_update_play_progress);
            //handler.sendEmptyMessageDelayed(msg_update_play_progress,50);
        }else {
            audio_player_iv_pause.setImageResource(R.drawable.audio_play_selector);
            stopWaveAnimation();
            handler.removeMessages(msg_update_play_progress);
    }
    }

    /**
     * 播放
     */
    private void startWaveAnimation() {
        AnimationDrawable animationDrawable = (AnimationDrawable) audio_player_wave.getDrawable();
        animationDrawable.start();
    }

    /**
     * 停止
     */
    private void stopWaveAnimation() {
        AnimationDrawable animationDrawable = (AnimationDrawable) audio_player_wave.getDrawable();
        animationDrawable.stop();
    }

    /**
     * 播放进度
     */
    private void startUpdatePlayProgress() {
        //初始化
        int currentPosition = audioBinder.getCurrentPosition();
        int duration = audioBinder.getDuration();
        String currentPositionTime = StringUtil.formatTime(currentPosition);
        String durationTime = StringUtil.formatTime(duration);
        audio_player_tv_position.setText(currentPositionTime+"/"+durationTime);
        audio_player_sk_position.setProgress(currentPosition);
        audio_player_sk_position.setMax(duration);
        handler.sendEmptyMessageDelayed(msg_update_play_progress,50);
    }

    /**
     * 上一曲
     */
    private void playPre() {
        audioBinder.playPre();
    }

    /**
     * 下一曲
     */
    private void playNext() {
        audioBinder.playNext();
    }

    /**
     * 切换播放模式
     */
    private void switchPlayMode() {
        audioBinder.switchPlayMode();
        switchPlayModePic();
    }

    /**
     * 切换播放模式图片
     */
    private void switchPlayModePic() {
        switch (audioBinder.getCurrentPlayMode()){
            case AudioPlayerService.play_mode_repeat:
                audio_player_iv_playmode.setImageResource(R.drawable.audio_playmode_allrepeat_selector);
                showToast("列表循环");
                break;
            case AudioPlayerService.play_mode_single:
                audio_player_iv_playmode.setImageResource(R.drawable.audio_playmode_singlerepeat_selector);
                showToast("单曲循环");
                break;
            case AudioPlayerService.play_mode_random:
                audio_player_iv_playmode.setImageResource(R.drawable.audio_playmode_random_selector);
                showToast("随机播放");
                break;
        }
    }
}
