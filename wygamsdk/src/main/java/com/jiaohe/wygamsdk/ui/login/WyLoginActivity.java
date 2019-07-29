package com.jiaohe.wygamsdk.ui.login;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.mvp.login.LoginPresenterImp;
import com.jiaohe.wygamsdk.mvp.login.LoginView;
import com.jiaohe.wygamsdk.mvp.login.UserBean;

/**
 * 登录页面
 */
public class WyLoginActivity extends SdkBaseActivity implements LoginView {
    private LoginPresenterImp loginPresenterImp;
    private Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_login;
    }

    @Override
    public void initViews() {
        btnLogin = findViewById(R.id.bsgamesdk_buttonLogin);
    }

    @Override
    public void initListener() {
        setOnClick(btnLogin);
    }

    @Override
    public void initData() {
        loginPresenterImp = new LoginPresenterImp();
        loginPresenterImp.attachView(this);
    }

    @Override
    public void processClick(View view) {
        loginPresenterImp.login("19942232155", "123456", this);
    }

    @Override
    public void showAppInfo(String msg, String data) {

    }

    @Override
    public void loginSuccess(int code, String msg, UserBean userBean) {
        Log.i("single", msg);
    }

    @Override
    public void loginFailed(int code, String msg) {
        Log.i("single", msg);
    }
}
