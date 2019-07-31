package com.jiaohe.wygamsdk.mvp.trumpet;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface TrumpetCreatePresenter extends BasePresenter<TrumpetCreateView> {
    void createTrumpet(String player_id, String children_account_name,Context context);
}
