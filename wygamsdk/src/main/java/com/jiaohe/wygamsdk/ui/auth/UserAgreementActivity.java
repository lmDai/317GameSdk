package com.jiaohe.wygamsdk.ui.auth;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.mvp.auth.AgreeMentBean;
import com.jiaohe.wygamsdk.mvp.auth.AgreePresenterImp;
import com.jiaohe.wygamsdk.mvp.auth.AgreeView;
import com.jiaohe.wygamsdk.widget.ResourceUtil;

/**
 * @package: com.jiaohe.wygamsdk.ui.auth
 * @user:xhkj
 * @date:2019/10/31
 * @description:用户协议
 **/
public class UserAgreementActivity extends SdkBaseActivity implements AgreeView {
    private TextView txtAgreeContent, txtAgreeTitle;
    private AgreePresenterImp agreePresenterImp;
    private ImageButton ibWebBack;

    @Override
    public int getLayoutId() {
        return ResourceUtil.getLayoutIdByName(this,"wygamesdk_user_agreement");
    }

    @Override
    public void initViews() {
        txtAgreeContent = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_id_agree_content"));
        txtAgreeTitle = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_id_agree_title"));
        ibWebBack = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_ib_loading_back"));
    }

    @Override
    public void initListener() {
        setOnClick(ibWebBack);
    }

    @Override
    public void initData() {
        agreePresenterImp = new AgreePresenterImp();
        agreePresenterImp.attachView(this);
        agreePresenterImp.getAgreeMent(this);
    }

    @Override
    public void processClick(View view) {
        int id = view.getId();
        if (id == R.id.wygamesdk_ib_loading_back) {
            finish();
        }
    }

    @Override
    public void showAgreeMent(int errorCode, String errorMsg, AgreeMentBean bean) {
        txtAgreeContent.setText(bean.getData().getContent());
        txtAgreeTitle.setText(bean.getData().getTitle());
    }

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);
    }
}
