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
 * @date:2019/10/31
 * @description:
 **/
public class BindPresenterImp implements BindPresenter {
    private BindView bindView;

    @Override
    public void bindPhone(String phone, String code, String player_id, Context context) {
        OkGo.<BaseResponse>post(Urls.URL_BIND_PHONE)//
                .tag(this)
                .params("phone", phone)
                .params("code", code)
                .params("player_id", player_id)
                .params("client", "Android")
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)
                .execute(new DialogCallback<BaseResponse>(context, "验证码发送中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse> response) {
                        bindView.bindSuccess(response.body().msg,response.body().code);
                    }
                });
    }

    @Override
    public void getCode(String phone, String player_id, Context context) {
        OkGo.<BaseResponse>post(Urls.URL_GET_BIND_CODE)//
                .tag(this)
                .params("phone", phone)
                .params("player_id", player_id)
                .params("client", "Android")
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)
                .execute(new DialogCallback<BaseResponse>(context, "验证码发送中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse> response) {
                        bindView.getCodeSuccess(response.body().msg,response.body().code);
                    }
                });
    }

    @Override
    public void attachView(BindView bindView) {
        this.bindView = bindView;
    }

    @Override
    public void detachView() {
        bindView = null;
    }
}
