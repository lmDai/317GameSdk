package com.jiaohe.wygamsdk.ui.menu;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.mvp.menu.MenuBean;
import com.jiaohe.wygamsdk.mvp.menu.MenuPresenterImp;
import com.jiaohe.wygamsdk.mvp.menu.MenuView;
import com.jiaohe.wygamsdk.tools.UserManage;
import com.jiaohe.wygamsdk.ui.adapter.MenuListAdapter;
import com.jiaohe.wygamsdk.widget.MultipleStatusView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;

/**
 * 主界面
 */
public class WyMenuWindowActivity extends SdkBaseActivity implements MenuView {
    private MenuPresenterImp menuPresenterImp;
    private String player_id;
    private ListView menuListView;
    private ImageView imgHead;
    private TextView txtNickName, txtTrumpetName;
    private LinearLayout llClose;
    private MultipleStatusView multipleStatusView;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_menu_window;
    }

    @Override
    public void initViews() {
        imgHead = findViewById(R.id.bsgamesdk_id_username_head);
        txtNickName = findViewById(R.id.bsgamesdk_id_username_nickname);
        menuListView = findViewById(R.id.wygamesdk_id_listview);
        txtTrumpetName = findViewById(R.id.bsgamesdk_id_trumpet_name);
        llClose = findViewById(R.id.bsgamesdk_id_close);
        multipleStatusView = findViewById(R.id.bsgamesdk_id_status_view);
    }

    @Override
    public void initListener() {
        setOnClick(llClose);
    }

    @Override
    public void initData() {
        menuPresenterImp = new MenuPresenterImp();
        menuPresenterImp.attachView(this);
        player_id = UserManage.getInstance().getSdkUserInfo(this).player_id;
        menuPresenterImp.getMenu(player_id, this);
        OkGo.<Bitmap>get(UserManage.getInstance().getSdkUserInfo(this).headimgurl)
                .tag(this)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        imgHead.setImageBitmap(response.body());
                    }
                });
        txtNickName.setText(UserManage.getInstance().getSdkUserInfo(this).username);
        txtTrumpetName.setText(UserManage.getInstance().getTrumpetName(this));
    }

    @Override
    public void processClick(View view) {
        int id = view.getId();
        if (id == R.id.bsgamesdk_id_close) {//关闭当前页面
            finish();
        }
    }

    @Override
    public void getMenuSuccess(MenuBean menuBean) {
        multipleStatusView.showContent();
        MenuListAdapter adapter = new MenuListAdapter(menuBean.list, this);
        menuListView.setAdapter(adapter);
    }

    @Override
    public void showLoadingLayout() {
        multipleStatusView.showLoading();
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuPresenterImp.detachView();
    }
}
