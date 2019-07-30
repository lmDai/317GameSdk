package com.jiaohe.wygamsdk.mvp.register;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface PhoneRegPresenter extends BasePresenter<PhoneRegView> {
    void getCode(String phone, Context context);

    void registByPhone(String phone, String code, String password, Context context);
}
