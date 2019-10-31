package com.jiaohe.wygamsdk.ui.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.jiaohe.wygamsdk.mvp.login.ForgetPasswordImp;
import com.jiaohe.wygamsdk.mvp.login.ForgetPasswordView;
import com.jiaohe.wygamsdk.tools.EncodeUtils;

/**
 * @package: com.jiaohe.wygamsdk.ui.login
 * @user:xhkj
 * @date:2019/7/30
 * @description:忘记密码
 **/
public class WyForgetPasswordActivity extends SdkBaseActivity implements ForgetPasswordView {
    private RelativeLayout rlBack, rlClose;
    private Button btnReg;
    private TextView txtRegAccount, txtGetCode;
    private EditText editRegName, editRegPwd, editRegCode;
    private ForgetPasswordImp forgetPasswordPresenter;
    private CountTimer countTimer;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_forget_password;
    }

    @Override
    public void initViews() {
        rlBack = findViewById(R.id.bsgamesdk_title_back);
        rlClose = findViewById(R.id.bsgamesdk_id_close);
        btnReg = findViewById(R.id.bsgamesdk_buttonReg);
        txtGetCode = findViewById(R.id.bsgamesdk_textview_reg_code);
        txtRegAccount = findViewById(R.id.bsgamesdk_textview_reg_account);
        editRegName = findViewById(R.id.bsgamesdk_reg_username);
        editRegCode = findViewById(R.id.bsgamesdk_edit_code_reg);
        editRegPwd = findViewById(R.id.bsgamesdk_edit_password_reg);
    }

    class CountTimer extends CountDownTimer {

        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * 倒计时过程中调用
         *
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            int time = (int) (Math.round((double) millisUntilFinished / 1000) - 1);
            txtGetCode.setText(String.valueOf(time) + "s");
            //设置倒计时中的按钮外观
            txtGetCode.setClickable(false);//倒计时过程中将按钮设置为不可点击
        }

        /**
         * 倒计时完成后调用
         */
        @Override
        public void onFinish() {
            txtGetCode.setText("重发验证码");
            txtGetCode.setClickable(true);
        }
    }

    @Override
    public void initListener() {
        setOnClick(rlBack);
        setOnClick(rlClose);
        setOnClick(btnReg);
        setOnClick(txtGetCode);
        setOnClick(txtRegAccount);
    }

    @Override
    public void initData() {
        forgetPasswordPresenter = new ForgetPasswordImp();
        forgetPasswordPresenter.attachView(this);
    }

    @Override
    public void processClick(View view) {
        int i = view.getId();
        String mUserName = editRegName.getText().toString().trim();
        String mPassWord = editRegPwd.getText().toString().trim();
        String mCode = editRegCode.getText().toString().trim();
        if (i == R.id.bsgamesdk_title_back) {//返回
            startActivity(new Intent(this, WyLoginActivity.class));
            onBackPressed();
        } else if (i == R.id.bsgamesdk_id_close) {//关闭
            onBackPressed();
        } else if (i == R.id.bsgamesdk_buttonReg) {//注册

            if (TextUtils.isEmpty(mUserName)) {
                showToast("请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(mCode)) {
                showToast("请输入验证码");
                return;
            }
            if (TextUtils.isEmpty(mPassWord)) {
                showToast("请输入密码");
                return;
            }
            forgetPasswordPresenter.playerForget(mUserName, mCode, mPassWord, this);
        } else if (i == R.id.bsgamesdk_textview_service) {//用户协议

        } else if (i == R.id.bsgamesdk_textview_reg_code) {//获取验证码
            if (EncodeUtils.isMobileNO(mUserName)) {
                forgetPasswordPresenter.sendforget(mUserName, this);
            } else {
                showToast("请输入正确的手机号");
            }
        } else if (i == R.id.bsgamesdk_textview_reg_account) {//账号注册
            startActivity(new Intent(this, WyAccountRegisterActivity.class));
            finish();
        }
    }


    @Override
    public void getCodeSuccess(int code, String msg) {
        showToast(msg);
        if (code == BaseResponse.SUCCESS) {
            countTimer = new CountTimer(60000, 1000);
            countTimer.start();
        }
    }

    @Override
    public void findPasswordSuccess(int code, String msg) {
        showToast(msg);
        if (code == BaseResponse.SUCCESS) {
            startActivity(new Intent(this, WyLoginActivity.class));
            onBackPressed();
        } else {
            editRegCode.setText("");
            editRegPwd.setText("");
        }
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countTimer != null)
            countTimer.cancel();
        forgetPasswordPresenter.detachView();
    }
}
