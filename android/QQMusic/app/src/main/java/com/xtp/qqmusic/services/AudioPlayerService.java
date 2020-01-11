package com.xtp.qqmusic.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.xtp.qqmusic.R;
import com.xtp.qqmusic.activity.AudioPlayerActivity;
import com.xtp.qqmusic.bean.AudioItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * 服务
 */
public class AudioPlayerService extends Service {
    public static final String Action_Audio_Prepared_BroadCast = "com.xtp.qqmusic_Action_Audio_Prepared_BroadCast";

    public static final int play_mode_repeat=0;//列表循环
    public static final int play_mode_single=1;//单曲循环
    public static final int play_mode_random=2;//随机循环
    public int currentPlayMode = play_mode_repeat;//默认为列表循环
    private MediaPlayer mediaPlayer ;//播放器
    private AudioItem audioItem;//歌曲详情
    private AudioBinder audioBinder;//绑定
    private ArrayList<AudioItem> audioItems;//歌曲列表
    private int position;//当前播放位置
    private int lastPosition=-1;//上一次播放位置
    private NotificationManager notificationManager ; //通知

    private static final int flag_layout = 0;
    private static final int flag_pre = 1;
    private static final int flag_next = 2;

    private class AudioOnPreparedListener implements MediaPlayer.OnPreparedListener {

        /**
         * mediaPlayer  == mp
         * @param mp
         */
        @Override
        public void onPrepared(MediaPlayer mp) {
            //播放
            mp.start();
            //发送广播，通知界面更新
            sendAudioPreparedBoradCast();
            //显示通知
            showNotification();
        }
    }

    /**
     * activity调用service的桥梁
     */
    public class AudioBinder extends Binder{
        /**
         * 切换播放和暂停
         */
        public void switchPlayAndPause(){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                //取消通知
                cancleNotification();
            }else {
                mediaPlayer.start();
                showNotification();
            }
        }

        /**
         *处理播放功能
         */
        private void playerItem() {
            audioItem= audioItems.get(position);
            //在此记录上一首位置
            lastPosition=position;
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(audioItem.getPath());
                //同步准备
                //mediaPlayer.prepare();
                //异步准备
                mediaPlayer.prepareAsync();
                //mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断当前状态(播放或暂停)
         */
        public boolean isPlaying(){
            return mediaPlayer.isPlaying();
        }

        /**
         * 获取当前进度
         */
        public int getCurrentPosition(){
            return mediaPlayer.getCurrentPosition();
        }

        /**
         * 获取总时长
         */
        public int getDuration(){
            return mediaPlayer.getDuration();
        }

        /**
         * 拖拽seekBar
         * @param progress
         */
        public void seekTo(int progress){
            mediaPlayer.seekTo(progress);
        }

        /**
         * 上一曲
         */
        public void playPre() {
            if(position==0){
                Toast.makeText(AudioPlayerService.this,"亲，已经是第一首了,请确认!",Toast.LENGTH_SHORT).show();
            }else {
                position--;
                playerItem();
            }

        }

        /**
         * 下一曲
         */
        public void playNext() {
            if(position==audioItems.size()-1){
                Toast.makeText(AudioPlayerService.this,"亲，已经是最后一首了,请确认!",Toast.LENGTH_SHORT).show();
            }else {
                position++;
                playerItem();
            }
        }

        /**
         * 切换播放模式
         */
        public void switchPlayMode(){
            switch (currentPlayMode){
                case play_mode_repeat:
                    currentPlayMode=play_mode_single;
                    break;
                case play_mode_single:
                    currentPlayMode=play_mode_random;
                    break;
                case play_mode_random:
                    currentPlayMode=play_mode_repeat;
                    break;
            }
        }

        /**
         * 获取当前播放模式
         */
        public int getCurrentPlayMode(){
            return currentPlayMode;
        }

        /**
         * 自动播放下一首
         */
        private void autoPlayNext() {
            switch (currentPlayMode){
                case play_mode_repeat:
                    if(position!=audioItems.size()-1){
                        position++;
                    }else {
                        position=0;
                    }
                    playerItem();
                    break;
                case play_mode_single:
                    playerItem();
                    break;
                case play_mode_random:
                    Random random = new Random();
                    position = random.nextInt(audioItems.size());
                    playerItem();
                    break;
            }
        }

        /**
         * 继续播放
         */
        public void continuePlaying() {
            mediaPlayer.start();
        }
    }

    /**
     * 播放完成
     */

    private class AudioOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            //根据当前播放模式,自动切换到下一首
            audioBinder.autoPlayNext();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new AudioOnPreparedListener());
        mediaPlayer.setOnCompletionListener(new AudioOnCompletionListener());
        audioBinder = new AudioBinder();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int flag = intent.getIntExtra("flag",-1);
        switch (flag){
            case -1:
                //通过Intent获取数据
                //audioItem = (AudioItem) intent.getSerializableExtra("AudioItem");
                //audioBinder.playerItem();
                audioItems = (ArrayList<AudioItem>) intent.getSerializableExtra("AudioItems");
                position = intent.getIntExtra("position", 0);
                //判断当前位置是否上一次播放位置
                if(position!=lastPosition){
                    //播放当前歌曲
                    audioBinder.playerItem();
                }else{
                    //不要重复播放，但需要更新界面
                    sendAudioPreparedBoradCast();
                    //如果不是正在播放，则继续播放
                    if(!audioBinder.isPlaying()){
                        audioBinder.continuePlaying();
                    }
                }
                break;
            case flag_layout:  //点击大布局
                sendAudioPreparedBoradCast();
                break;
            case flag_pre:  //点击上一曲
                audioBinder.playPre();
                break;
            case flag_next:  //点击下一曲
                audioBinder.playNext();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return audioBinder;
    }

    //发送通知广播
    private void sendAudioPreparedBoradCast() {
        Intent intent = new Intent(Action_Audio_Prepared_BroadCast);
        intent.putExtra("AudioItem",audioItem);
        sendBroadcast(intent);
    }


    /**
     * 显示通知
     */
    public void showNotification(){
        Notification notification = getNotificationWithNewApi();
        notificationManager.notify(0,notification);
    }

    /**
     * 取消通知
     */
    public void cancleNotification(){
        notificationManager.cancel(0);
    }

    private Notification getNotificationWithNewApi() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //flag
        builder.setOngoing(true)
                //状态栏内容:int icon, CharSequence tickerText, long when(系统当前时间,立马弹出)
                .setSmallIcon(R.drawable.icon)
                .setTicker("正在播放:"+audioItem.getTitle())
                .setWhen(System.currentTimeMillis())
                //通知栏内容:自定义布局
                .setContent(getRemoteViews());
        return builder.build();
    }

    /**
     * RemoteViews 远程的view ,自定义view
     * @return
     */
    private RemoteViews getRemoteViews() {
        //String packageName, int layoutId:自定义布局id
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.audio_notify);
        //设置RemoteViews textview值
        remoteViews.setTextViewText(R.id.audio_notify_tv_title,audioItem.getTitle());
        remoteViews.setTextViewText(R.id.audio_notify_tv_arties,audioItem.getArtist());
        //设置 点击事件
        remoteViews.setOnClickPendingIntent(R.id.audio_notify_layout,getLayoutPendingIntent());
        remoteViews.setOnClickPendingIntent(R.id.audio_notify_iv_pre,getPrePendingIntent());
        remoteViews.setOnClickPendingIntent(R.id.audio_notify_iv_next,getNextPendingIntent());
        return remoteViews;
    }

    private PendingIntent getLayoutPendingIntent() {
        Intent intent = new Intent(this, AudioPlayerActivity.class);
        intent.putExtra("flag",flag_layout);
        //requstCode:请求码
        //flag:FLAG_UPDATE_CURRENT  会覆盖相同请求码的intent传递的数据
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    private PendingIntent getPrePendingIntent() {
        Intent intent = new Intent(this,AudioPlayerService.class);
        intent.putExtra("flag",flag_pre);
        PendingIntent contentIntent = PendingIntent.getService(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    private PendingIntent getNextPendingIntent() {
        Intent intent = new Intent(this,AudioPlayerService.class);
        intent.putExtra("flag",flag_next);
        PendingIntent contentIntent = PendingIntent.getService(this,2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }
}
