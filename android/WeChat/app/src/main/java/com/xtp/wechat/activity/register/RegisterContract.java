package com.xtp.wechat.activity.register;

import android.app.Activity;
import android.content.Intent;

import com.xtp.wechat.BasePresenter;
import com.xtp.wechat.BaseView;

public interface RegisterContract {
    int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    int PHOTO_REQUEST_CUT = 3;// 结果

    interface View extends BaseView<Presenter>{
        void showAvatar(String imagePath);
        void showDialog();
        void cancelDialog();
        void showPassword();
        void hidePassword();
        void enableButton();
        void disableButton();
        void showToast(int msgRes);
        String getOriginImagePath();
        Activity getBaseActivity();

    }

    interface Presenter extends BasePresenter{
        void registerInServer(String nickName,String mobile,String password);
        void result(int requsetCode, int resultCode, Intent intent);
    }
}
