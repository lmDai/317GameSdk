package com.jiaohe.wygamsdk.mvp.login;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/10/22
 * @description:
 **/
public interface ForgetPasswordPresenter extends BasePresenter<ForgetPasswordView> {
    //忘记密码短信
    void sendforget(String username, Context context);

    //忘记密码
    void playerForget(String username, String smscode, String password, Context mContext);
}
