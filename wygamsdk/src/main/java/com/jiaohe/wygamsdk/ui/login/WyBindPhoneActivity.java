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
import com.jiaohe.wygamsdk.mvp.login.BindPresenterImp;
import com.jiaohe.wygamsdk.mvp.login.BindView;
import com.jiaohe.wygamsdk.tools.EncodeUtils;
import com.jiaohe.wygamsdk.tools.UserManage;
import com.jiaohe.wygamsdk.ui.auth.UserAgreementActivity;

/**
 * @package: com.jiaohe.wygamsdk.ui.login
 * @user:xhkj
 * @date:2019/10/31
 * @description:绑定手机号
 **/
public class WyBindPhoneActivity extends SdkBaseActivity implements BindView {
    private RelativeLayout rlClose;
    private Button btnReg;
    private TextView txtGetCode;
    private EditText editRegName, editRegCode;
    private CountTimer countTimer;
    private BindPresenterImp bindPresenterImp;
    private String player_id;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_bind_phone;
    }

    @Override
    public void initViews() {
        rlClose = findViewById(R.id.wygamesdk_id_close);
        btnReg = findViewById(R.id.wygamesdk_buttonReg);
        txtGetCode = findViewById(R.id.wygamesdk_textview_reg_code);
        editRegName = findViewById(R.id.wygamesdk_reg_username);
        editRegCode = findViewById(R.id.wygamesdk_edit_code_reg);
    }

    @Override
    public void initListener() {
        setOnClick(rlClose);
        setOnClick(btnReg);
        setOnClick(txtGetCode);
    }

    @Override
    public void initData() {
        bindPresenterImp = new BindPresenterImp();
        bindPresenterImp.attachView(this);
        player_id = UserManage.getInstance().getPlayerId(this);
    }

    @Override
    public void processClick(View view) {
        int i = view.getId();
        String mUserName = editRegName.getText().toString().trim();
        String mCode = editRegCode.getText().toString().trim();
        if (i == R.id.wygamesdk_id_close) {//关闭
            onBackPressed();
        } else if (i == R.id.wygamesdk_buttonReg) {//确定
            if (TextUtils.isEmpty(mUserName)) {
                showToast("请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(mCode)) {
                showToast("请输入验证码");
                return;
            }
            bindPresenterImp.bindPhone(mUserName, mCode, player_id, this);
        }  else if (i == R.id.wygamesdk_textview_reg_code) {//获取验证码
            if (EncodeUtils.isMobileNO(mUserName)) {
                bindPresenterImp.getCode(mUserName, player_id, this);
            } else {
                showToast("请输入正确的手机号");
            }
        }
    }

    @Override
    public void getCodeSuccess(String msg, int errorCode) {
        showToast(msg);
        countTimer = new CountTimer(60000, 1000);
        countTimer.start();
    }

    @Override
    public void getCodeFailed(String msg, int errorCode) {
        showToast(msg);
    }

    @Override
    public void bindSuccess(String msg, int errorCode) {
        showToast(msg);
        this.finish();
    }

    @Override
    public void bindFailed(String msg, int errorCode) {
        showToast(msg);
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);
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
    protected void onDestroy() {
        super.onDestroy();
        if (countTimer != null)
            countTimer.cancel();
    }
}
