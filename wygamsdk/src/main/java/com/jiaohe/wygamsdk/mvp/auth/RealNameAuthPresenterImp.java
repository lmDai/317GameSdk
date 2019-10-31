package com.jiaohe.wygamsdk.mvp.auth;

import android.content.Context;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * @package: com.jiaohe.wygamsdk.mvp.auth
 * @user:xhkj
 * @date:2019/10/23
 * @description:实名认证
 **/
public class RealNameAuthPresenterImp implements RealNamAuthPresenter {
    private RealNameAuthView realNameAuthView;

    @Override
    public void realNameAuth(String player_id, String real_name, String certificate, Context mContext) {
        OkGo.<BaseResponse>post(Urls.URL_REALNAME_AUTH)//
                .tag(this)
                .params("player_id", player_id)
                .params("real_name", real_name)
                .params("certificate", certificate)
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)
                .execute(new DialogCallback<BaseResponse>(mContext, "加载中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse> response) {
                        realNameAuthView.authResult(response.body().errorCode, response.body().errorMsg);
                    }
                });
    }

    @Override
    public void attachView(RealNameAuthView realNameAuthView) {
        this.realNameAuthView = realNameAuthView;
    }

    @Override
    public void detachView() {
        realNameAuthView = null;
    }
}
