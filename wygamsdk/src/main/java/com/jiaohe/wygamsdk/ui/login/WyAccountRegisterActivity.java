package com.jiaohe.wygamsdk.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.call.Delegate;
import com.jiaohe.wygamsdk.config.SDKStatusCode;
import com.jiaohe.wygamsdk.mvp.login.UserBean;
import com.jiaohe.wygamsdk.mvp.register.AccountRegPresenterImp;
import com.jiaohe.wygamsdk.mvp.register.AccountRegView;
import com.jiaohe.wygamsdk.tools.UserManage;

/**
 * @package: com.jiaohe.wygamsdk.ui.login
 * @user:xhkj
 * @date:2019/7/30
 * @description:账号注册
 **/
public class WyAccountRegisterActivity extends SdkBaseActivity implements AccountRegView {
    private RelativeLayout rlBack, rlClose;
    private EditText editRegName, editRegPwd, editRegConfimPwd;
    private Button btnReg;
    private TextView txtRegPhone, txtService;
    private AccountRegPresenterImp accountRegPresenterImp;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_account_resigster;
    }

    @Override
    public void initViews() {
        rlBack = findViewById(R.id.bsgamesdk_title_back);
        rlClose = findViewById(R.id.bsgamesdk_id_close);
        editRegName = findViewById(R.id.bsgamesdk_reg_username);
        editRegPwd = findViewById(R.id.bsgamesdk_edit_password_reg);
        editRegConfimPwd = findViewById(R.id.bsgamesdk_edit_password_again_reg);
        btnReg = findViewById(R.id.bsgamesdk_buttonReg);
        txtService = findViewById(R.id.bsgamesdk_textview_service);
        txtRegPhone = findViewById(R.id.bsgamesdk_textview_reg_phone);
    }

    @Override
    public void initListener() {
        setOnClick(rlBack);
        setOnClick(rlClose);
        setOnClick(btnReg);
        setOnClick(txtService);
        setOnClick(txtRegPhone);
    }

    @Override
    public void initData() {
        accountRegPresenterImp = new AccountRegPresenterImp();
        accountRegPresenterImp.attachView(this);
    }

    @Override
    public void processClick(View view) {
        int i = view.getId();
        if (i == R.id.bsgamesdk_title_back) {//返回
            startActivity(new Intent(this, WyLoginActivity.class));
            onBackPressed();
        } else if (i == R.id.bsgamesdk_id_close) {//关闭
            onBackPressed();
        } else if (i == R.id.bsgamesdk_buttonReg) {//注册
            String mUserName = editRegName.getText().toString().trim();
            String mPassWord = editRegPwd.getText().toString().trim();
            String mSecPassWord = editRegConfimPwd.getText().toString().trim();
            if (TextUtils.isEmpty(mUserName)) {
                showToast("请输入账号");
                return;
            }
            if (TextUtils.isEmpty(mPassWord)) {
                showToast("请输入密码");
                return;
            }
            if (TextUtils.isEmpty(mSecPassWord)) {
                showToast("再次输入密码");
                return;
            }
            if (!TextUtils.equals(mPassWord, mSecPassWord)) {
                showToast("两次密码输入不一致");
                return;
            }
            accountRegPresenterImp.regist(mUserName, mPassWord, mSecPassWord, this);
        } else if (i == R.id.bsgamesdk_textview_service) {//用户协议

        } else if (i == R.id.bsgamesdk_textview_reg_phone) {//手机号注册
            startActivity(new Intent(this, WyPhoneRegisterActivity.class));
            finish();
        }
    }

    @Override
    public void regSuccess(int code, String msg, UserBean userBean) {
        Delegate.listener.callback(SDKStatusCode.SUCCESS, userBean.player_id);
        UserManage.getInstance().saveUserInfo(this, editRegName.getText().toString(), editRegPwd.getText().toString());
        UserManage.getInstance().saveUserInfo(this, userBean.player_id,
                userBean.username, userBean.phone,
                userBean.nickname, userBean.headimgurl,
                userBean.is_validate);
        //            Intent intent = new Intent();
//            intent.putExtra("player_id", userBean.player_id);
//            intent.setClass(this, SdkTrumpetListActivity.class);
//            startActivity(intent);
    }

    @Override
    public void regFailed(int code, String msg) {

    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accountRegPresenterImp.detachView();
    }
}
