package com.jiaohe.wygamsdk.mvp.login;

import android.content.Context;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.jiaohe.wygamsdk.tools.EncodeUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class LoginPresenterImp implements LoginPresenter {
    private LoginView loginView;

    @Override
    public void login(String username, String password, Context context) {
        OkGo.<BaseResponse<UserBean>>post(Urls.URL_LOGIN)//
                .tag(this)//
                .params("username", username)//
                .params("password", EncodeUtils.md5(password))//
                .params("gameId", ConfigInfo.gameID)//
                .params("channelId", ConfigInfo.channelID)//
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<BaseResponse<UserBean>>(context, "登录中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse<UserBean>> response) {
                        loginView.loginSuccess(response.body().errorCode, response.body().errorMsg, response.body().data);
                    }

                    @Override
                    public void onError(Response<BaseResponse<UserBean>> response) {
                        loginView.loginFailed(response.body().errorCode, response.body().errorMsg);
                    }
                });
    }

    @Override
    public void attachView(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void detachView() {
        this.loginView = null;
    }
}
