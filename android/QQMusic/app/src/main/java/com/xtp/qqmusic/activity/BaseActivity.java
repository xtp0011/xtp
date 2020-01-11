package com.xtp.qqmusic.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xtp.qqmusic.R;
import com.xtp.qqmusic.utils.LogUtils;
import com.xtp.qqmusic.utils.PermisionUtils;

/**
 * Activity 的基类
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        //1.查找控件
        initView();
        //2.设置监听
        initListener();
        //3.设置数据
        initData();
        //4.处理相同逻辑
        dealCommon();
    }



    /**
     * 获取当前资源id
     * @return
     */
    public abstract int getLayoutResId() ;

    /**
     * 查找控件
     */
    public abstract void initView();

    /**
     * 设置监听
     */
    public abstract void  initListener() ;


    /**
     * 设置数据
     */
    public abstract void initData();


    /**
     * 处理相同逻辑
     */
    private  void dealCommon(){
        View back = findViewById(R.id.back);
        if(back==null) return;
        if(back instanceof Button){
            ((Button) back).setText("返回");
        }
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            default:
                onInnerClick(view);
                break;
        }
    }

    /**
     * 非相同逻辑重写
     */
    public void onInnerClick(View view){
    }

    /**
     * 弹出对话框
     * @param msg
     */
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 打印日志
     * @param msg
     */
    public void logInfo(String msg){
        LogUtils.i(getClass(),msg);
    }

}
