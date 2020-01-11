package com.xtp.wechat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.xtp.wechat.BaseActivity;
import com.xtp.wechat.R;
import com.xtp.wechat.activity.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        AlphaAnimation animation = new AlphaAnimation(0.5f, 1.0f);
        animation.setDuration(2000);
        rootLayout.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                if (HTClient.getInstance().isLogined()) {
//                    //上传最近登录时间
//                    UpdateLastLoginTimeUtils.sendLocalTimeToService(SplashActivity.this);
//                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
               // }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
