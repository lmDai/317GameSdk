package com.jiaohe.wygamsdk.mvp.login;

import com.jiaohe.wygamsdk.base.BaseView;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/10/31
 * @description:
 **/
public interface BindView extends BaseView {
    void getCodeSuccess(String msg, int errorCode);

    void getCodeFailed(String msg, int errorCode);

    void bindSuccess(String msg, int errorCode);

    void bindFailed(String msg, int errorCode);
}
