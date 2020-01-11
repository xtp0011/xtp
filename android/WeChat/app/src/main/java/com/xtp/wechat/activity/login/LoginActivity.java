package com.xtp.wechat.activity.login;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.xtp.wechat.BaseActivity;
import com.xtp.wechat.R;

public class LoginActivity extends BaseActivity {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        hideBackView();
        setTitle(R.string.login_by_mobile);
       LoginFragment loginFragment = (LoginFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
       if(loginFragment==null){
           loginFragment = new LoginFragment();
           FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
           fragmentTransaction.add(R.id.contentFrame,loginFragment);
           fragmentTransaction.commit();
       }
       loginPresenter = new LoginPresenter(loginFragment);
    }
}
