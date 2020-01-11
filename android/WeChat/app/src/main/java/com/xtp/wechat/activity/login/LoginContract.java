package com.xtp.wechat.activity.login;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.xtp.wechat.BasePresenter;
import com.xtp.wechat.BaseView;

public interface LoginContract  {

    interface View extends BaseView<Presenter> {

        void showDialog();

        void cancelDialog();

        String getUsername();

        String getPassword();

        void setButtonEnable();

        void setButtonDisabel();

        void showToast(int toastMsg);

        String getCountryName();

        String getCountryCode();

        Activity getBaseActivity();

        Context getBaseContext();


    }

    interface Presenter extends BasePresenter {

        void requestServer(String username, String password);

        void chooseCuntry(Context context, TextView tvCountryName, TextView tvCountryCode);

    }
}
