package com.htmessage.yichatopen.activity.main.password;

import com.htmessage.yichatopen.activity.BaseView;

/**
 * 项目名称：HTOpen
 * 类描述：PasswordView 描述:
 * 创建人：songlijie
 * 创建时间：2017/7/7 15:08
 * 邮箱:814326663@qq.com
 */
public interface PasswordView extends BaseView<PasswordPrester>{
    String getCountryName();
    String getCountryCode();
    String getCacheCode();
    String getSMSCode();
    boolean getIsReset();
    String getMobile();
    String getPwd();
    String getConfimPwd();
    void clearCacheCode();
    void onSendSMSCodeSuccess(String msg);
    void onSendSMSCodeFailed(String error);
    void onResetSuccess(String msg);
    void onResetFailed(String error);
    void startTimeDown();
    void finishTimeDown();
    void onLogOutFailed(String msg);
}
