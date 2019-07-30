package com.jiaohe.wygamsdk.mvp.register;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface AccountRegPresenter extends BasePresenter<AccountRegView> {
    void regist(String username, String pwd, String repwd,Context context);
}
