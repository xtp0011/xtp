package com.xtp.wechat.activity.register;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtp.wechat.R;
import com.xtp.wechat.util.Validator;

public class RegisterFragment extends Fragment implements View.OnClickListener,RegisterContract.View {

    private EditText et_usernick, et_usertel, et_password;
    private Button btn_register;
    private ImageView iv_hide, iv_show, iv_photo;
    private TextView tv_xieyi, tv_country, tv_country_code;
    private RelativeLayout rl_country;

    private RegisterContract.Presenter mPresenter;
    //选取的原始图片
    private String imagePathOrigin = null;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        et_usernick =  root.findViewById(R.id.et_usernick);
        et_usertel = root.findViewById(R.id.et_usertel);
        et_password =  root.findViewById(R.id.et_password);
        //获取国家code
        tv_country =  root.findViewById(R.id.tv_country);
        tv_country_code =  root.findViewById(R.id.tv_country_code);
        rl_country =  root.findViewById(R.id.rl_country);
        btn_register = root.findViewById(R.id.btn_register);
        tv_xieyi =  root.findViewById(R.id.tv_xieyi);
        iv_hide =  root.findViewById(R.id.iv_hide);
        iv_show =  root.findViewById(R.id.iv_show);
        iv_photo =  root.findViewById(R.id.iv_photo);
        initView();
        setLisenter();
        return root;
    }

    private void initView() {
        String xieyi = "<font color=" + "\"" + "#AAAAAA" + "\">" + getString(R.string.press_top)
                + "&nbsp;" + "\"" + getString(R.string.register) + "\"" + "&nbsp;" + getString(R.string.btn_means_agree) + "</font>" + "<u>"
                + "<font color=" + "\"" + "#576B95" + "\">" + getString(R.string.Secret_agreement)
                + "</font>" + "</u>";
        tv_xieyi.setText(Html.fromHtml(xieyi));
    }

    private void setLisenter() {
        // 监听多个输入框
        TextChange textChange = new TextChange();
        et_usernick.addTextChangedListener(textChange);
        et_usertel.addTextChangedListener(textChange);
        et_password.addTextChangedListener(textChange);

        tv_country.setOnClickListener(this);
        tv_country_code.setOnClickListener(this);
        rl_country.setOnClickListener(this);
        iv_hide.setOnClickListener(this);
        iv_show.setOnClickListener(this);
        iv_photo.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hide:
                hidePassword();
                editTextEnd();
                break;
            case R.id.iv_show:
                showPassword();
                editTextEnd();
                break;
            case R.id.btn_register:
                String usernick = et_usernick.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String usertel = et_usertel.getText().toString().trim();
                String country = tv_country.getText().toString().trim();
                String countryCode = tv_country_code.getText().toString().trim();

//                if (TextUtils.isEmpty(usertel)) {
//                    showToast(R.string.mobile_not_be_null);
//                    return;
//                }
//                if (country.equals(getString(R.string.china)) && countryCode.equals(getString(R.string.country_code))) {
//                    if (!Validator.isMobile(usertel)) {
//                        showToast(R.string.please_input_true_mobile);
//                        return;
//                    }
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    showToast(R.string.pwd_is_not_allow_null);
//                    return;
//                }

                mPresenter.registerInServer(usernick, usertel, password);
                break;
            case R.id.rl_country:
               // CommonUtils.showPup(getContext(), tv_country, tv_country_code);
                break;

            case R.id.iv_photo:
               // showCamera();
                break;

        }

    }

        // 切换后将密码EditText光标置于末尾
        private void editTextEnd(){
            CharSequence charSequence = et_password.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
        }

    @Override
    public void showAvatar(String imagePath) {
        //Glide.with(getActivity()).load(imagePath).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.default_image).into(iv_photo);
    }

    @Override
    public void showDialog() {
        if (dialog != null)
            dialog.show();
    }

    @Override
    public void cancelDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void showPassword() {
        iv_show.setVisibility(View.GONE);
        iv_hide.setVisibility(View.VISIBLE);
        et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    public void hidePassword() {
            iv_hide.setVisibility(View.GONE);
            iv_show.setVisibility(View.VISIBLE);
            et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    @Override
    public void enableButton() {
        btn_register.setEnabled(true);
    }

    @Override
    public void disableButton() {
        btn_register.setEnabled(false);
    }

    @Override
    public void showToast(int msgRes) {
        Toast.makeText(getActivity(), msgRes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getOriginImagePath() {
        return imagePathOrigin;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Context getBaseContext() {
        return getContext();
    }

    @Override
    public Activity getBaseActivity() {
        return getActivity();
    }


    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {

            boolean sign1 = et_usernick.getText().length() > 0;
            boolean sign2 = et_usertel.getText().length() > 0;
            boolean sign3 = et_password.getText().length() > 0;

            if (sign1 & sign2 & sign3) {

                enableButton();
            } else {
                disableButton();
            }
        }

    }
}
