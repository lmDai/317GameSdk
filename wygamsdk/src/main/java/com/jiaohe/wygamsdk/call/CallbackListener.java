package com.jiaohe.wygamsdk.call;

import android.os.Bundle;

/**
 * @package: com.jiaohe.wygamsdk.call
 * @user:xhkj
 * @date:2019/9/29
 * @description:
 **/
public interface CallbackListener {
    void onSuccess(Bundle bundle);

    void onFailed(WYGameSdkError wyGameSdkError);

    void onError(WYGameSdkError wyGameSdkError);
}
