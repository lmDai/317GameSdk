package com.jiaohe.wygamsdk.mvp.trumpet;

import android.content.Context;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @Trumpet:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class TrumpetPresenterImp implements TrumpetPresenter {
    private TrumpetView trumpetView;


    @Override
    public void attachView(TrumpetView trumpetView) {
        this.trumpetView = trumpetView;
    }

    @Override
    public void detachView() {
        this.trumpetView = null;
    }

    @Override
    public void getTrumpList(String player_id, Context context) {
        OkGo.<BaseResponse<List<TrumpetBean>>>post(Urls.URL_TRUMPET_LIST)//
                .tag(this)//
                .params("player_id", player_id)//
                .params("gameId", ConfigInfo.gameID)//
                .params("channelId", ConfigInfo.channelID)//
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<BaseResponse<List<TrumpetBean>>>(context, "数据加载中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<TrumpetBean>>> response) {
                        trumpetView.getTrumpetSuccess(response.body().data);
                    }
                });
    }
}
