package com.jiaohe.wygamsdk.mvp.login;

import com.jiaohe.wygamsdk.base.BaseView;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/10/22
 * @description:
 **/
public interface ForgetPasswordView  extends BaseView {
    void getCodeSuccess(int code, String msg);

    void findPasswordSuccess(int code, String msg);
}
