package com.xtp.qqmusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.xtp.qqmusic.utils.LogUtils;

public abstract class BaseFragment extends Fragment {

    public Context context;

    /**
     * 确定fragment的布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=getContext();
        View view = View.inflate(getActivity(),getLayoutResId(),null);
        //1.查找控件
        initView(view);
        //2.设置监听
        initListener();
        //3.设置数据
        initData();
        return view ;
    }

    /**
     * 获取当前资源id
     * @return
     */
    public abstract int getLayoutResId() ;

    /**
     * 查找控件
     */
    public abstract void initView(View view);

    /**
     * 设置监听
     */
    public abstract void  initListener() ;


    /**
     * 设置数据
     */
    public abstract void initData();


    /**
     * 弹出对话框
     * @param msg
     */
    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }

    /**
     * 打印日志
     * @param msg
     */
    public void logInfo(String msg){
        LogUtils.i(getClass(),msg);
    }

}
