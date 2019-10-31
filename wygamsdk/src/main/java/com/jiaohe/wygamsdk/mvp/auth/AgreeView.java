package com.jiaohe.wygamsdk.mvp.auth;

import com.jiaohe.wygamsdk.base.BaseView;

/**
 * @package: com.jiaohe.wygamsdk.mvp.auth
 * @user:xhkj
 * @date:2019/10/31
 * @description:
 **/
public interface AgreeView extends BaseView {
    void showAgreeMent(int errorCode, String errorMsg, AgreeMentBean bean);
}
