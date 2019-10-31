package com.jiaohe.wygamsdk.mvp.login;

import android.content.Context;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/10/22
 * @description: 找回密码
 **/
public class ForgetPasswordImp implements ForgetPasswordPresenter {
    private ForgetPasswordView forgetPasswordView;

    @Override
    public void attachView(ForgetPasswordView forgetPasswordView) {
        this.forgetPasswordView = forgetPasswordView;
    }

    @Override
    public void detachView() {
        forgetPasswordView = null;
    }

    @Override
    public void sendforget(String username, Context context) {
        OkGo.<BaseResponse>post(Urls.URL_FORGET_PASSWORD_CODE)//
                .tag(this)
                .params("username", username)
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)
                .execute(new DialogCallback<BaseResponse>(context, "验证码发送中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse> response) {
                        forgetPasswordView.getCodeSuccess(response.body().errorCode, response.body().errorMsg);
                    }
                });
    }

    @Override
    public void playerForget(String username, String smscode, String password, Context mContext) {
        OkGo.<BaseResponse>post(Urls.URL_PLAYER_FORGET)//
                .tag(this)
                .params("username", username)
                .params("smscode", smscode)
                .params("password", password)
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)
                .execute(new DialogCallback<BaseResponse>(mContext, "验证码发送中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse> response) {
                        forgetPasswordView.findPasswordSuccess(response.body().errorCode, response.body().errorMsg);
                    }
                });
    }
}
