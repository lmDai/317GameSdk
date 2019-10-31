package com.jiaohe.wygamsdk.mvp.auth;

import android.content.Context;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * @package: com.jiaohe.wygamsdk.mvp.auth
 * @user:xhkj
 * @date:2019/10/23
 * @description:用户协议
 **/
public class AgreePresenterImp implements AgreePresenter {
    private AgreeView agreeView;


    @Override
    public void attachView(AgreeView agreeView) {
        this.agreeView = agreeView;
    }


    @Override
    public void detachView() {
        agreeView = null;
    }

    @Override
    public void getAgreeMent(Context mContext) {
        OkGo.<AgreeMentBean>post(Urls.URL_USER_AGREE_MENT)//
                .tag(this)
                .isMultipart(false)
                .execute(new DialogCallback<AgreeMentBean>(mContext, "加载中...") {
                    @Override
                    public void onSuccess(Response<AgreeMentBean> response) {
                        agreeView.showAgreeMent(response.body().getCode(), response.body().getMsg(),
                                response.body());
                    }
                });
    }
}
