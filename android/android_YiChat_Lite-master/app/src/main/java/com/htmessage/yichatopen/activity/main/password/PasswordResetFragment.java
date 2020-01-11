package com.htmessage.yichatopen.activity.main.password;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmessage.yichatopen.HTApp;
import com.htmessage.yichatopen.R;
import com.htmessage.yichatopen.HTConstant;
import com.htmessage.yichatopen.utils.ACache;
import com.htmessage.yichatopen.utils.CommonUtils;

/**
 * 项目名称：HTOpen
 * 类描述：PasswordResetFragment 描述:
 * 创建人：songlijie
 * 创建时间：2017/7/7 15:33
 * 邮箱:814326663@qq.com
 */
public class PasswordResetFragment extends Fragment implements PasswordView ,OnClickListener{
    private Button btn_ok, btn_code;
    private EditText et_code,et_usertel,et_password,et_password_confire;
     private String mobile;
    private TextView tv_title;
    private ACache aCache;
    private TextView tv_country,tv_country_code;
    private RelativeLayout rl_country,rl_smscode;
    private PasswordPrester prester;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pswView = inflater.inflate(R.layout.activity_psw_reset, container, false);
        getData();
        initView(pswView);
        initData();
        setListener();
        return pswView;
    }

    private void setListener() {
        tv_country.setOnClickListener(this);
        tv_country_code.setOnClickListener(this);
        rl_country.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_code.setOnClickListener(this);
    }

    private void initData() {
        rl_smscode.setVisibility(View.GONE);
         if (getIsReset()) {
            tv_title.setText(R.string.resetPassword);
            btn_ok.setText(R.string.resetPassword);
            mobile = HTApp.getInstance().getUserJson().getString(HTConstant.JSON_KEY_TEL);
            et_usertel.setText(mobile);
            et_usertel.setEnabled(false);
            et_usertel.clearFocus();
        } else {
            tv_title.setText(R.string.find_pwd);
            btn_ok.setText(R.string.find_pwd);
            et_usertel.setHint(R.string.input_bind_mobile);
            et_usertel.setEnabled(true);
            et_usertel.requestFocus();
        }
    }

    private void initView(View pswView) {
        //获取国家code
        tv_country = (TextView) pswView.findViewById(R.id.tv_country);
        tv_country_code = (TextView) pswView.findViewById(R.id.tv_country_code);
        rl_country = (RelativeLayout) pswView.findViewById(R.id.rl_country);
        rl_smscode = (RelativeLayout) pswView.findViewById(R.id.rl_smscode);
        et_usertel = (EditText) pswView.findViewById(R.id.et_usertel);
        et_password = (EditText) pswView.findViewById(R.id.et_password);
        et_password_confire = (EditText) pswView.findViewById(R.id.et_password_confire);
        et_code = (EditText) pswView.findViewById(R.id.et_code);
        btn_ok = (Button) pswView.findViewById(R.id.btn_ok);
        btn_code = (Button) pswView.findViewById(R.id.btn_code);
        tv_title= (TextView) pswView.findViewById(R.id.tv_title);
    }

    private void getData() {
        aCache = ACache.get(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_country:
                    CommonUtils.showPup(getBaseActivity(),tv_country,tv_country_code);
                break;
            case R.id.btn_code:
                prester.sendSMSCode(getMobile(),getCountryName(),getCountryCode());
                break;
            case R.id.btn_ok:
                prester.resetPassword(getCacheCode(),getSMSCode(),getPwd(),getConfimPwd(),getMobile());
                break;
        }
    }

    @Override
    public String getCountryName() {
        return tv_country.getText().toString().trim();
    }

    @Override
    public String getCountryCode() {
        return tv_country_code.getText().toString().trim();
    }

    @Override
    public String getCacheCode() {
        return  aCache.getAsString("code");
    }

    @Override
    public String getSMSCode() {
        return et_code.getText().toString().trim();
    }

    @Override
    public boolean getIsReset() {
        return getActivity().getIntent().getBooleanExtra("isReset", false);
    }

    @Override
    public String getMobile() {
        return et_usertel.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return et_password.getText().toString().trim();
    }

    @Override
    public String getConfimPwd() {
        return et_password_confire.getText().toString().trim();
    }

    @Override
    public void clearCacheCode() {
        aCache.put("code","");
    }

    @Override
    public void onSendSMSCodeSuccess(String msg) {
        if ("1234".equals(msg)){
            et_code.setText(msg);
        }
        aCache.put("code",msg);
        Toast.makeText(getBaseContext(), R.string.code_is_send, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendSMSCodeFailed(String error) {
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResetSuccess(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResetFailed(String error) {
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startTimeDown() {

    }

    @Override
    public void finishTimeDown() {

    }


    @Override
    public void onLogOutFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(PasswordPrester presenter) {
        this.prester = presenter;
    }

    @Override
    public Context getBaseContext() {
        return getContext();
    }

    @Override
    public Activity getBaseActivity() {
        return getActivity();
    }
}
