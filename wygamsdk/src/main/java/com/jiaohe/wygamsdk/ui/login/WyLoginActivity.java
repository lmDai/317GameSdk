package com.jiaohe.wygamsdk.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.call.Delegate;
import com.jiaohe.wygamsdk.call.WYGameSdkError;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.mvp.login.LoginPresenterImp;
import com.jiaohe.wygamsdk.mvp.login.LoginView;
import com.jiaohe.wygamsdk.mvp.login.UserBean;
import com.jiaohe.wygamsdk.tools.UserManage;
import com.jiaohe.wygamsdk.ui.trumpet.WyTrumpetActivity;
import com.jiaohe.wygamsdk.widget.EditableSpinner;

/**
 * 登录页面
 */
public class WyLoginActivity extends SdkBaseActivity implements LoginView {
    private LoginPresenterImp loginPresenterImp;
    private Button btnLogin;
    private EditableSpinner mEditableSpinner;
    private EditText editPassword;
    private TextView txtQuikRegister, txtForgetPwd;
    private RelativeLayout rlClose;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_login;
    }

    @Override
    public void initViews() {
        btnLogin = findViewById(R.id.bsgamesdk_buttonLogin);
        editPassword = findViewById(R.id.bsgamesdk_edit_password_login);
        txtQuikRegister = findViewById(R.id.bsgamesdk_textview_quickRegister);
        txtForgetPwd = findViewById(R.id.bsgamesdk_textview_login_forgetPwd);
        mEditableSpinner = findViewById(R.id.bsgamesdk_edit_username_spinner);
        rlClose = findViewById(R.id.bsgamesdk_id_close);
    }

    @Override
    public void initListener() {
        setOnClick(btnLogin);
        setOnClick(txtQuikRegister);
        setOnClick(txtForgetPwd);
        setOnClick(rlClose);
    }

    @Override
    public void initData() {
        loginPresenterImp = new LoginPresenterImp();
        loginPresenterImp.attachView(this);
        String localPwd = UserManage.getInstance().getLoginPassword(this);
        String localUserName = UserManage.getInstance().getLoginUserName(this);
        editPassword.setText(localPwd);
        mEditableSpinner.setText(localUserName);
        String[] mOrders = {};
        ArrayAdapter<String> mOrderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mOrders);
        mEditableSpinner.setAdapter(mOrderAdapter)
                .setOnItemClickListener(new EditableSpinner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // .....
                    }
                });
    }

    @Override
    public void processClick(View view) {
        int i = view.getId();
        if (i == R.id.bsgamesdk_buttonLogin) {//登录
            String username = mEditableSpinner.getSelectedItem().toString();//用户名
            String password = editPassword.getText().toString();//密码
            if (TextUtils.isEmpty(username)) {
                showToast("请输入账号或手机号");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                showToast("请输入密码");
                return;
            }
            loginPresenterImp.login(username, password, this);
        } else if (i == R.id.bsgamesdk_textview_quickRegister) {//立即注册
            startActivity(new Intent(this, WyAccountRegisterActivity.class));
            onBackPressed();
        } else if (i == R.id.bsgamesdk_textview_login_forgetPwd) {//忘记密码
            startActivity(new Intent(this, WyForgetPasswordActivity.class));
            onBackPressed();
        } else if (i == R.id.bsgamesdk_id_close) {
            onBackPressed();
        }
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);
    }

    @Override
    public void loginSuccess(int code, String msg, UserBean userBean) {
        ConfigInfo.userID = userBean.player_id;
        UserManage.getInstance().saveUserInfo(this, mEditableSpinner.getSelectedItem(), editPassword.getText().toString());
        UserManage.getInstance().saveUserInfo(this, userBean.player_id,
                userBean.username, userBean.phone,
                userBean.nickname, userBean.headimgurl,
                userBean.is_validate);
        showToast(msg);
        Intent intent = new Intent();
        intent.putExtra("player_id", userBean.player_id);
        intent.setClass(this, WyTrumpetActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed(int code, String msg) {
        Log.i("single", msg);
        Delegate.callbackListener.onFailed(new WYGameSdkError(code, msg));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenterImp.detachView();
    }
}
