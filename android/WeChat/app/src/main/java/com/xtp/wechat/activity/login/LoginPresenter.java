package com.xtp.wechat.activity.login;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.xtp.wechat.activity.main.MainActivity;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;

    public LoginPresenter(LoginContract.View loginView){
        this.loginView=loginView;
        this.loginView.setPresenter(this);
    }

    @Override
    public void requestServer(String username, String password) {
        Intent intent = new Intent(loginView.getBaseContext(), MainActivity.class);
        loginView.getBaseActivity().startActivity(intent);
        loginView.getBaseActivity().finish();
    }

    @Override
    public void chooseCuntry(Context context, TextView tvCountryName, TextView tvCountryCode) {

    }

    @Override
    public void start() {

    }
}
