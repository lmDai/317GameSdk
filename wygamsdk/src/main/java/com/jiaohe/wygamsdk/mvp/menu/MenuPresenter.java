package com.jiaohe.wygamsdk.mvp.menu;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface MenuPresenter extends BasePresenter<MenuView> {
    void getMenu(String player_id, Context context);
}
