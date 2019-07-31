package com.jiaohe.wygamsdk.mvp.trumpet;

import android.content.Context;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @Trumpet:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class TrumpetCreatePresenterImp implements TrumpetCreatePresenter {
    private TrumpetCreateView trumpetCreateView;


    @Override
    public void attachView(TrumpetCreateView trumpetCreateView) {
        this.trumpetCreateView = trumpetCreateView;
    }

    @Override
    public void detachView() {
        this.trumpetCreateView = null;
    }


    @Override
    public void createTrumpet(String player_id, String children_account_name, Context context) {
        OkGo.<BaseResponse<TrumpetBean>>post(Urls.URL_TRUMPET_CREATE)//
                .tag(this)//
                .params("player_id", player_id)//
                .params("gameId", ConfigInfo.gameID)//
                .params("children_account_name", children_account_name)//
                .params("channelId", ConfigInfo.channelID)//
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<BaseResponse<TrumpetBean>>(context, "数据加载中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse<TrumpetBean>> response) {
                        trumpetCreateView.createSuccess(response.body().errorMsg, response.body().data);
                    }
                });
    }
}
