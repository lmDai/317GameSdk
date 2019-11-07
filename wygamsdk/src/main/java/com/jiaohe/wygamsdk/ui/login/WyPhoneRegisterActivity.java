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
import com.jiaohe.wygamsdk.call.Delegate;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.SDKStatusCode;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.jiaohe.wygamsdk.mvp.login.UserBean;
import com.jiaohe.wygamsdk.mvp.register.PhoneRegPresenterImp;
import com.jiaohe.wygamsdk.mvp.register.PhoneRegView;
import com.jiaohe.wygamsdk.tools.EncodeUtils;
import com.jiaohe.wygamsdk.tools.UserManage;
import com.jiaohe.wygamsdk.ui.auth.UserAgreementActivity;
import com.jiaohe.wygamsdk.ui.trumpet.WyTrumpetActivity;
import com.jiaohe.wygamsdk.widget.ResourceUtil;

/**
 * @package: com.jiaohe.wygamsdk.ui.login
 * @user:xhkj
 * @date:2019/7/30
 * @description:手机号注册
 **/
public class WyPhoneRegisterActivity extends SdkBaseActivity implements PhoneRegView {
    private RelativeLayout rlBack, rlClose;
    private Button btnReg;
    private TextView txtRegAccount, txtService, txtGetCode;
    private EditText editRegName, editRegPwd, editRegCode;
    private PhoneRegPresenterImp phoneRegPresenterImp;
    private CountTimer countTimer;

    @Override
    public int getLayoutId() {
        return ResourceUtil.getLayoutIdByName(this,"wygamesdk_phone_resigster");
    }

    @Override
    public void initViews() {
        rlBack = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_title_back"));
        rlClose = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_id_close"));
        btnReg = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_buttonReg"));
        txtService = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_textview_service"));
        txtGetCode = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_textview_reg_code"));
        txtRegAccount = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_textview_reg_account"));
        editRegName = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_reg_username"));
        editRegCode = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_edit_code_reg"));
        editRegPwd = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_edit_password_reg"));
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
        setOnClick(txtService);
        setOnClick(txtGetCode);
        setOnClick(txtRegAccount);
    }

    @Override
    public void initData() {
        phoneRegPresenterImp = new PhoneRegPresenterImp();
        phoneRegPresenterImp.attachView(this);
    }

    @Override
    public void processClick(View view) {
        int i = view.getId();
        String mUserName = editRegName.getText().toString().trim();
        String mPassWord = editRegPwd.getText().toString().trim();
        String mCode = editRegCode.getText().toString().trim();
        if (i == ResourceUtil.getViewIdByName(this,"wygamesdk_title_back")) {//返回
            startActivity(new Intent(this, WyAccountRegisterActivity.class));
            onBackPressed();
        } else if (i == ResourceUtil.getViewIdByName(this,"wygamesdk_id_close")) {//关闭
            onBackPressed();
        } else if (i == ResourceUtil.getViewIdByName(this,"wygamesdk_buttonReg")) {//注册

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
            phoneRegPresenterImp.registByPhone(mUserName, mCode, mPassWord, this);
        } else if (i == ResourceUtil.getViewIdByName(this,"wygamesdk_textview_service")) {//用户协议
            startActivity(new Intent(this, UserAgreementActivity.class));
        } else if (i == ResourceUtil.getViewIdByName(this,"wygamesdk_textview_reg_code")) {//获取验证码
            if (EncodeUtils.isMobileNO(mUserName)) {
                phoneRegPresenterImp.getCode(mUserName, this);
            } else {
                showToast("请输入正确的手机号");
            }
        } else if (i == ResourceUtil.getViewIdByName(this,"wygamesdk_textview_reg_account")) {//账号注册
            startActivity(new Intent(this, WyAccountRegisterActivity.class));
            finish();
        }
    }

    @Override
    public void regSuccess(int code, String msg, UserBean userBean) {
        showToast(msg);
        if (code == BaseResponse.SUCCESS) {
            ConfigInfo.userID = userBean.player_id;//设置玩家ID常量
            UserManage.getInstance().saveUserInfo(this, editRegName.getText().toString(), editRegPwd.getText().toString());
            UserManage.getInstance().saveUserInfo(this, userBean.player_id,
                    userBean.username, userBean.phone,
                    userBean.nickname, userBean.headimgurl,
                    userBean.is_validate,userBean.token);
            Intent intent = new Intent();
            intent.putExtra("player_id", userBean.player_id);
            intent.setClass(this, WyTrumpetActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Delegate.listener.callback(SDKStatusCode.FAILURE, "");
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
    public void showAppInfo(String msg, String data) {
        showToast(msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countTimer != null)
            countTimer.cancel();
        phoneRegPresenterImp.detachView();
    }
}
