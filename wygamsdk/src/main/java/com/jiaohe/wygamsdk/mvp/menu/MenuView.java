package com.jiaohe.wygamsdk.mvp.menu;

import com.jiaohe.wygamsdk.base.BaseView;
import com.jiaohe.wygamsdk.mvp.login.UserBean;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface MenuView extends BaseView {
    void getMenuSuccess(MenuBean menuBean);
    void showLoadingLayout();
}
