package com.jiaohe.wygamsdk.mvp.menu;

import android.content.Context;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class MenuPresenterImp implements MenuPresenter {
    private MenuView menuView;

    @Override
    public void attachView(MenuView menuView) {
        this.menuView = menuView;
    }

    @Override
    public void detachView() {
        this.menuView = null;
    }

    @Override
    public void getMenu(String player_id, Context context) {
        OkGo.<BaseResponse<MenuBean>>post(Urls.URL_BUOY_INDEX)//
                .tag(this)//
                .params("player_id", player_id)//
                .params("gameId", ConfigInfo.gameID)//
                .params("channelId", ConfigInfo.channelID)//
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<BaseResponse<MenuBean>>(context, "数据加载中...") {
                    @Override
                    public void onStart(Request<BaseResponse<MenuBean>, ? extends Request> request) {
                        menuView.showLoadingLayout();
                    }

                    @Override
                    public void onSuccess(Response<BaseResponse<MenuBean>> response) {
                        menuView.getMenuSuccess(response.body().data);
                    }
                });
    }
}
