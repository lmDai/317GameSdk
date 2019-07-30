package com.jiaohe.wygamsdk.mvp.register;

import com.jiaohe.wygamsdk.base.BaseView;
import com.jiaohe.wygamsdk.mvp.login.UserBean;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface AccountRegView extends BaseView {
    void regSuccess(int code, String msg, UserBean userBean);

    void regFailed(int code, String msg);
}
