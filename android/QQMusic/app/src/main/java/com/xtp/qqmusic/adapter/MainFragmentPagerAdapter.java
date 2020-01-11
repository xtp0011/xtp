package com.xtp.qqmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.xtp.qqmusic.fragment.BaseFragment;
import java.util.List;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    /**
     * 数据源
     * @param fm
     * @param fragments
     */
    public MainFragmentPagerAdapter(FragmentManager fm,List<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    /**
     * 有几个界面
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public boolean isViewFromObject(View view,Object object) {
        return super.isViewFromObject(view, object);
    }

    /**
     * 获取条目，根据position获取对应的fragment
     * @return
     */
    @Override
    public int getCount() {
        return fragments.size();
    }
}
