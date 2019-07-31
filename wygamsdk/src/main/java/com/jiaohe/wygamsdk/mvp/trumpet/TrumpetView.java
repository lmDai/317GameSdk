package com.jiaohe.wygamsdk.mvp.trumpet;

import com.jiaohe.wygamsdk.base.BaseView;

import java.util.List;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface TrumpetView extends BaseView {
    void getTrumpetSuccess(List<TrumpetBean> trumpetList);

}
