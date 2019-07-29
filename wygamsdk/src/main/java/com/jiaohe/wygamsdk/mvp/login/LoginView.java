package com.jiaohe.wygamsdk.mvp.login;

import com.jiaohe.wygamsdk.base.BaseView;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface LoginView extends BaseView {
    void loginSuccess(int code, String msg, UserBean userBean);

    void loginFailed(int code, String msg);
}
