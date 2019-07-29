package com.jiaohe.wygamsdk.callback;

/**
 * @package: com.jiaohe.wygamsdk.callback
 * @user:xhkj
 * @date:2019/7/26
 * @description:全局回调
 **/
public interface SdkCallbackListener<T> {
    void callback(int code, T response);
}
