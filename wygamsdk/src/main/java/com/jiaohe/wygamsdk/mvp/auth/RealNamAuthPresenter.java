package com.jiaohe.wygamsdk.mvp.auth;

import android.content.Context;

import com.jiaohe.wygamsdk.base.BasePresenter;

/**
 * @package: com.jiaohe.wygamsdk.mvp.auth
 * @user:xhkj
 * @date:2019/10/23
 * @description:实名认证
 **/
public interface RealNamAuthPresenter extends BasePresenter<RealNameAuthView> {
    void realNameAuth(String player_id,String real_name, String certificate, Context mContext);
}
