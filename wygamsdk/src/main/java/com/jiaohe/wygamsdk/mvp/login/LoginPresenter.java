package com.jiaohe.wygamsdk.mvp.login;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface LoginPresenter extends BasePresenter<LoginView> {
    void login(String username, String password, Context context);
}
