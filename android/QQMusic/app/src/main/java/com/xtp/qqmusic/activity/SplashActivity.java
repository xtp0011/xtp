package com.xtp.qqmusic.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.xtp.qqmusic.R;

public class SplashActivity extends BaseActivity{

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_DELAYED_JUMP:
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    private static final int MSG_DELAYED_JUMP=0;

    @Override
    protected void onResume() {
        super.onResume();
        //handler.sendMessageDelayed();
        handler.sendEmptyMessageDelayed(MSG_DELAYED_JUMP,2000);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
