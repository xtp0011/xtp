package com.xtp.qqmusic.fragment;

import android.view.View;
import android.widget.TextView;

import com.xtp.qqmusic.R;

/**
 * 视频列表
 */
public class VideoListFragment extends BaseFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.video_list;
    }

    @Override
    public void initView(View view) {
        TextView textView = view.findViewById(R.id.simple_listview);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
