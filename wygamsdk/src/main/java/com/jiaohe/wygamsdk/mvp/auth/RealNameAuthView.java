package com.jiaohe.wygamsdk.mvp.auth;

import com.jiaohe.wygamsdk.base.BaseView;

/**
 * @package: com.jiaohe.wygamsdk.mvp.auth
 * @user:xhkj
 * @date:2019/10/23
 * @description:实名认证
 **/
public interface RealNameAuthView extends BaseView {
    void authResult(int errorCode, String errorMsg);
}
