package com.xtp.wechat.activity.register;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xtp.wechat.BaseActivity;
import com.xtp.wechat.R;

public class RegisterActivity extends BaseActivity {

    private RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setTitle("用户注册");
        RegisterFragment registerFragment = (RegisterFragment) getFragmentManager().findFragmentById(R.id.contentFrame);
        if(registerFragment==null){
            registerFragment = new RegisterFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.contentFrame,registerFragment);
            fragmentTransaction.commit();
        }
        presenter = new RegisterPresenter(registerFragment);
    }
}
