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
public class PhoneRegPresenterImp implements PhoneRegPresenter {
    private PhoneRegView phoneRegView;

    @Override
    public void attachView(PhoneRegView phoneRegView) {
        this.phoneRegView = phoneRegView;
    }

    @Override
    public void detachView() {
        this.phoneRegView = null;
    }

    @Override
    public void getCode(String phone, Context context) {
        OkGo.<BaseResponse>post(Urls.URL_PHONE_REGISGER_CODE)//
                .tag(this)
                .params("phone", phone)
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<BaseResponse>(context, "验证码发送中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse> response) {
                        phoneRegView.getCodeSuccess(response.body().code, response.body().msg);
                    }
                });
    }

    @Override
    public void registByPhone(String phone, String code, String password, Context context) {
        OkGo.<BaseResponse<UserBean>>post(Urls.URL_PHONE_REGISGER)//
                .tag(this)
                .params("phone", phone)
                .params("code", code)
                .params("password", EncodeUtils.md5(password))
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<BaseResponse<UserBean>>(context, "正在注册...") {
                    @Override
                    public void onSuccess(Response<BaseResponse<UserBean>> response) {
                        phoneRegView.regSuccess(response.body().code, response.body().msg, response.body().data);
                    }
                });
    }
}
