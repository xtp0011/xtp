package com.xtp.qqmusic.activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.xtp.qqmusic.R;
import com.xtp.qqmusic.adapter.MainFragmentPagerAdapter;
import com.xtp.qqmusic.fragment.AudioListFragment;
import com.xtp.qqmusic.fragment.BaseFragment;
import com.xtp.qqmusic.fragment.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private ViewPager viewPager ;
    //数据源
    private List<BaseFragment> fragments = new ArrayList<>() ;

    private MainFragmentPagerAdapter adapter ;

    private TextView main_tv_video;

    private TextView main_tv_audio;

    private View main_indicate_line ;//指视线

    private int indicateLineWidth ;

    /**
     * 界面改变监听
     */
    private class MainOnPageChangeListener implements ViewPager.OnPageChangeListener{

        /**
         * 界面改变监听
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            //1.颜色改变
            int halfwhite = getResources().getColor(R.color.halfwhite);
            int green = getResources().getColor(R.color.green);
            main_tv_video.setTextColor(position ==0?green:halfwhite);
            main_tv_audio.setTextColor(position ==1?green:halfwhite);

            // 2.标签文本缩放动画(0 变大 ，1变小)
            //执行控件的动画
            ViewPropertyAnimator.animate(main_tv_video).scaleX(position==0?1.2f:1.0f).scaleY(position==0?1.2f:1.0f);
            ViewPropertyAnimator.animate(main_tv_audio).scaleX(position==1?1.2f:1.0f).scaleY(position==1?1.2f:1.0f);
        }


        /**
         * 界面滑动监听
         * @param position viewpager第一个界面的索引
         * @param positionOffSet  viewpager第一个界面偏移的宽度的百分比
         * @param positionOffPixels  viewpager第一个界面的的偏移度 px
         */
        @Override
        public void onPageScrolled(int position, float positionOffSet, int positionOffPixels) {
            float offSetX = indicateLineWidth*positionOffSet;
            float startX = indicateLineWidth*position;
            float endX =startX+offSetX;
            ViewHelper.setTranslationX(main_indicate_line,endX);
        }

        /**
         * 界面滑动状态改变
         * @param i
         */
        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
         //设置数据源
        fragments.add(new VideoListFragment());
        fragments.add(new AudioListFragment());
        //数据源变化刷新适配器
        adapter.notifyDataSetChanged();

        //设置指视线的默认宽度
        int screeWidth = getResources().getDisplayMetrics().widthPixels;
        indicateLineWidth = screeWidth/fragments.size();
        main_indicate_line.getLayoutParams().width=indicateLineWidth;

    }

    @Override
    public void initListener() {
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        //viewPager设置界面改变监听
        viewPager.setOnPageChangeListener(new MainOnPageChangeListener());
        //标签设置监听
        main_tv_video.setOnClickListener(this);
        main_tv_audio.setOnClickListener(this);
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.main_viewpager);
        main_tv_audio = findViewById(R.id.main_tv_audio);
        main_tv_video = findViewById(R.id.main_tv_video);
        ViewPropertyAnimator.animate(main_tv_video).scaleX(1.2f).scaleY(1.2f);
        main_indicate_line = findViewById(R.id.main_indicate_line);
    }

    @Override
    public void onInnerClick(View view) {
        switch (view.getId()){
            case R.id.main_tv_video:
                viewPager.setCurrentItem(0);
                break;
            case R.id.main_tv_audio:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
