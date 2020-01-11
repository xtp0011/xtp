package com.xtp.wechat.activity.register;

import android.content.Intent;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View registerView;
   // private String cropImagePath = null;

    public RegisterPresenter(RegisterContract.View registerView){
        this.registerView = registerView;
        this.registerView.setPresenter(this);
    }

    @Override
    public void registerInServer(String nickName, String mobile, String password) {
        registerView.getBaseActivity().finish();
    }

    @Override
    public void result(int requsetCode, int resultCode, Intent intent) {

    }

    @Override
    public void start() {

    }
}
