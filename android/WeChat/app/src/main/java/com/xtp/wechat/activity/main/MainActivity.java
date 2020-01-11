package com.xtp.wechat.activity.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.xtp.wechat.BaseActivity;
import com.xtp.wechat.R;

public class MainActivity extends BaseActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private String[] mTitles;
    private int[] drawabls = new int[]{R.drawable.tab_chat_bg, R.drawable.tab_contact_list_bg, R.drawable.tab_find_bg, R.drawable.tab_profile_bg};
    private TabLayout.Tab[] tabs;
    private Fragment[] fragments;
    //新消息角标
    private TextView unreadLabel;
    // 新好友申请消息角标
    public TextView unreadInvitionLable;

    // user logged into another device
    public boolean isConflict = false;
    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isConflictDialogShow;
    private MainPrestener mainPrestener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_main);
    }
}
