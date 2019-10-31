package com.jiaohe.wygamsdk.ui.menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    private RelativeLayout rlWallet;
    private MultipleStatusView multipleStatusView;
    private MenuBean menuBean;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_menu_window;
    }

    @Override
    public void initViews() {
        imgHead = findViewById(R.id.wygamesdk_id_username_head);
        txtNickName = findViewById(R.id.wygamesdk_id_username_nickname);
        menuListView = findViewById(R.id.wygamesdk_id_listview);
        txtTrumpetName = findViewById(R.id.wygamesdk_id_trumpet_name);
        llClose = findViewById(R.id.wygamesdk_id_close);
        multipleStatusView = findViewById(R.id.wygamesdk_id_status_view);
        rlWallet = findViewById(R.id.wygamesdk_id_menu_wallet);
    }

    @Override
    public void initListener() {
        setOnClick(llClose);
        setOnClick(rlWallet);
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
        if (id == R.id.wygamesdk_id_close) {//关闭当前页面
            finish();
        } else if (id == R.id.wygamesdk_id_menu_wallet) {//钱包
            if (menuBean != null && menuBean.wallet != null) {
                Intent intent = new Intent();
                intent.putExtra("url", menuBean.wallet.link);
                intent.setClass(WyMenuWindowActivity.this, WyWebActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void getMenuSuccess(final MenuBean menuBean) {
        this.menuBean = menuBean;
        multipleStatusView.showContent();
        MenuListAdapter adapter = new MenuListAdapter(menuBean.list, this);
        menuListView.setAdapter(adapter);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("url", menuBean.list.get(position).link);
                intent.setClass(WyMenuWindowActivity.this, WyWebActivity.class);
                startActivity(intent);
            }
        });
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
