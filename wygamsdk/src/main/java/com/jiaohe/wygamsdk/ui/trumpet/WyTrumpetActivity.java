package com.jiaohe.wygamsdk.ui.trumpet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.call.Delegate;
import com.jiaohe.wygamsdk.mvp.trumpet.TrumpetBean;
import com.jiaohe.wygamsdk.mvp.trumpet.TrumpetPresenterImp;
import com.jiaohe.wygamsdk.mvp.trumpet.TrumpetView;
import com.jiaohe.wygamsdk.tools.UserManage;
import com.jiaohe.wygamsdk.ui.adapter.TrumpetListAdapter;
import com.jiaohe.wygamsdk.ui.login.WyLoginActivity;
import com.jiaohe.wygamsdk.widget.AutoListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * 选择小号页面
 */
public class WyTrumpetActivity extends SdkBaseActivity implements TrumpetView {
    private static final int REQUEST_CODE = 0x001;
    private TrumpetPresenterImp trumpetPresenterImp;
    private String player_id;
    private ImageView imgHead;
    private TextView txtNickName, txtLogout;
    private AutoListView trumpetListView;
    private ImageButton imgBtnAdd;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_trumpet_list;
    }

    @Override
    public void initViews() {
        imgHead = findViewById(R.id.wygamesdk_id_username_head);
        txtNickName = findViewById(R.id.wygamesdk_id_username_nickname);
        trumpetListView = findViewById(R.id.wygamesdk_id_listview);
        txtLogout = findViewById(R.id.wygamesdk_id_btn_logout);
        imgBtnAdd = findViewById(R.id.wygamesdk_id_ib_add);
    }

    @Override
    public void initListener() {
        setOnClick(txtLogout);
        setOnClick(imgBtnAdd);
    }

    @Override
    public void initData() {
        trumpetPresenterImp = new TrumpetPresenterImp();
        trumpetPresenterImp.attachView(this);
        OkGo.<Bitmap>get(UserManage.getInstance().getSdkUserInfo(this).headimgurl)
                .tag(this)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        imgHead.setImageBitmap(response.body());
                    }
                });
        txtNickName.setText(UserManage.getInstance().getSdkUserInfo(this).nickname);
        player_id = getIntent().getStringExtra("player_id");
        trumpetPresenterImp.getTrumpList(player_id, this);
    }

    @Override
    public void processClick(View view) {
        int id = view.getId();
        if (id == R.id.wygamesdk_id_btn_logout) {//注销
            startActivity(new Intent(this, WyLoginActivity.class));
            finish();
        } else if (id == R.id.wygamesdk_id_ib_add) {//添加小号
            Intent intent = new Intent();
            intent.putExtra("player_id", player_id);
            intent.setClass(this, WyCreateTrumpetActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        trumpetPresenterImp.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            trumpetPresenterImp.getTrumpList(player_id, this);
        }
    }

    @Override
    public void getTrumpetSuccess(final List<TrumpetBean> trumpetList) {
        TrumpetListAdapter adapter = new TrumpetListAdapter(trumpetList, this);
        trumpetListView.setAdapter(adapter);
        trumpetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserManage.getInstance().saveTrumpetName(WyTrumpetActivity.this, trumpetList.get(position).accouut_name);
                UserManage.getInstance().saveChildrenId(WyTrumpetActivity.this, trumpetList.get(position).channel_accouut_id);
                Bundle bundle = new Bundle();
                bundle.putString("channel_accouut_id", trumpetList.get(position).channel_accouut_id);
                bundle.putString("player_id", trumpetList.get(position).player_id);
                bundle.putString("accouut_name", trumpetList.get(position).accouut_name);
                Delegate.callbackListener.onSuccess(bundle);
                finish();
            }
        });
    }
}
