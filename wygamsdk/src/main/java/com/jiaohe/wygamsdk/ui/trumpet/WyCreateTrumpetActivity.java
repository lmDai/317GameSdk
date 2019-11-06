package com.jiaohe.wygamsdk.ui.trumpet;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.mvp.trumpet.TrumpetBean;
import com.jiaohe.wygamsdk.mvp.trumpet.TrumpetCreatePresenterImp;
import com.jiaohe.wygamsdk.mvp.trumpet.TrumpetCreateView;
import com.jiaohe.wygamsdk.widget.ResourceUtil;

/**
 * 创建小号
 */
public class WyCreateTrumpetActivity extends SdkBaseActivity implements TrumpetCreateView {
    private TrumpetCreatePresenterImp trumpetCreatePresenterImp;
    private String player_id;
    private Button btnCreateTrumpet, btnCancelTrumpet;
    private EditText editCreateTrumpet;

    @Override
    public int getLayoutId() {
        return ResourceUtil.getLayoutIdByName(this,"wygamesdk_create_trumpet");
    }

    @Override
    public void initViews() {
        editCreateTrumpet = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_edit_trumpet_create"));
        btnCreateTrumpet = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_buttonCancel"));
        btnCancelTrumpet = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_buttonCreate"));
    }

    @Override
    public void initListener() {
        setOnClick(btnCreateTrumpet);
        setOnClick(btnCancelTrumpet);
    }

    @Override
    public void initData() {
        trumpetCreatePresenterImp = new TrumpetCreatePresenterImp();
        trumpetCreatePresenterImp.attachView(this);
        player_id = getIntent().getStringExtra("player_id");
    }

    @Override
    public void processClick(View view) {
        int id = view.getId();
        if (id == ResourceUtil.getViewIdByName(this,"wygamesdk_buttonCancel")) {//取消
            finish();
        } else if (id == ResourceUtil.getViewIdByName(this,"wygamesdk_buttonCreate")) {//确定
            String trumpetName = editCreateTrumpet.getText().toString();
            if (TextUtils.isEmpty(trumpetName)) {
                showToast("请输入小号名称");
                return;
            }
            trumpetCreatePresenterImp.createTrumpet(player_id,trumpetName,this);
        }
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        trumpetCreatePresenterImp.detachView();
    }

    @Override
    public void createSuccess(String msg, TrumpetBean trumpetBean) {
        showToast(msg);
        setResult(RESULT_OK);
        finish(); //结束当前的activity的生命周期
    }
}
