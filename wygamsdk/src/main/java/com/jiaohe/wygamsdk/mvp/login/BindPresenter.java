package com.jiaohe.wygamsdk.mvp.login;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/10/31
 * @description:
 **/
public interface BindPresenter extends BasePresenter<BindView> {
    void bindPhone(String phone, String code, String player_id, Context context);

    void getCode(String phone, String player_id, Context context);
}
