package com.xtp.wechat;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseActivity extends Activity {

    public void setTitle(int title){
        TextView textView= this.findViewById(R.id.tv_title);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.addRule();
//        imageView.setLayoutParams(layoutParams);
        if(textView!=null){
            textView.setText(title);
        }

    }

    public void back(View view){
        finish();
    }

    public void hideBackView(){
        ImageView iv_back= this.findViewById(R.id.iv_back);
        View view=this.findViewById(R.id.view_temp);
        if(iv_back!=null&&view!=null){
            iv_back.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }
}
