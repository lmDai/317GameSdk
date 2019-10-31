package com.jiaohe.wygamsdk.mvp.register;

import android.content.Context;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.jiaohe.wygamsdk.mvp.login.UserBean;
import com.jiaohe.wygamsdk.tools.EncodeUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class AccountRegPresenterImp implements AccountRegPresenter {
    private AccountRegView accountRegView;

    @Override
    public void regist(String username, String pwd, String repwd, Context context) {
        OkGo.<BaseResponse<UserBean>>post(Urls.URL_ACCOUNT_REGISTER)//
                .tag(this)
                .params("username", username)
                .params("password", EncodeUtils.md5(pwd))
                .params("repassword", EncodeUtils.md5(repwd))
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)
                .execute(new DialogCallback<BaseResponse<UserBean>>(context, "正在注册...") {
                    @Override
                    public void onSuccess(Response<BaseResponse<UserBean>> response) {
                        accountRegView.regSuccess(response.body().errorCode, response.body().errorMsg, response.body().data);
                    }
                });
    }

    @Override
    public void attachView(AccountRegView accountRegView) {
        this.accountRegView = accountRegView;
    }

    @Override
    public void detachView() {
        this.accountRegView = null;
    }
}
