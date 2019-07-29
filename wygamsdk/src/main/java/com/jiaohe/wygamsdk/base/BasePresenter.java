package com.jiaohe.wygamsdk.base;

/**
 * @package: com.jiaohe.wygamsdk.base
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public interface BasePresenter<T extends BaseView> {
    void attachView(T t);

    void detachView();
}
