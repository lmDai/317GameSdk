package com.jiaohe.wygamsdk.mvp.trumpet;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface TrumpetPresenter extends BasePresenter<TrumpetView> {
    void getTrumpList(String player_id, Context context);
}
