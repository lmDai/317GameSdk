package com.jiaohe.wygamsdk.mvp.register;

import com.jiaohe.wygamsdk.base.BaseView;
import com.jiaohe.wygamsdk.mvp.login.UserBean;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface PhoneRegView extends BaseView {
    void regSuccess(int code, String msg, UserBean userBean);

    void getCodeSuccess(int code, String msg);
}
