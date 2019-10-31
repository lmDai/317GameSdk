package com.jiaohe.wygamsdk.mvp.auth;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.auth
 * @user:xhkj
 * @date:2019/10/31
 * @description:
 **/
public interface AgreePresenter extends BasePresenter<AgreeView> {
    void getAgreeMent(Context mContext);
}
