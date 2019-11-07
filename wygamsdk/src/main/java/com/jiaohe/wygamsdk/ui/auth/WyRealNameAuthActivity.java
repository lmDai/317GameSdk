package com.jiaohe.wygamsdk.ui.auth;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.call.Delegate;
import com.jiaohe.wygamsdk.call.WYGameSdkError;
import com.jiaohe.wygamsdk.config.ConstData;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.jiaohe.wygamsdk.mvp.auth.RealNameAuthPresenterImp;
import com.jiaohe.wygamsdk.mvp.auth.RealNameAuthView;
import com.jiaohe.wygamsdk.mvp.login.UserBean;
import com.jiaohe.wygamsdk.tools.UserManage;
import com.jiaohe.wygamsdk.widget.ResourceUtil;

/**
 * @package: com.jiaohe.wygamsdk.ui.auth
 * @user:xhkj
 * @date:2019/10/23
 * @description:实名认证
 **/
public class WyRealNameAuthActivity extends SdkBaseActivity implements RealNameAuthView {
    private EditText editName, editIdNumber;
    private Button btnCancel, btnConfirm;
    private RealNameAuthPresenterImp authPresenterImp;
    private String player_id;

    @Override
    public void showAppInfo(String msg, String data) {
        showToast(msg);
    }

    @Override
    public int getLayoutId() {
        return ResourceUtil.getLayoutIdByName(this,"wygamesdk_realname_auth");
    }

    @Override
    public void initViews() {
        editName = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_edit_authentication_name"));
        editIdNumber = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_edit_authentication_id_number"));
        btnCancel = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_buttonCancel"));
        btnConfirm = findViewById(ResourceUtil.getViewIdByName(this,"wygamesdk_buttonCreate"));
    }

    @Override
    public void initListener() {
        setOnClick(btnCancel);
        setOnClick(btnConfirm);
    }

    @Override
    public void initData() {
        authPresenterImp = new RealNameAuthPresenterImp();
        authPresenterImp.attachView(this);
        player_id = UserManage.getInstance().getPlayerId(this);
    }

    @Override
    public void processClick(View view) {
        if (view.getId() ==ResourceUtil.getViewIdByName(this,"wygamesdk_buttonCancel")) {//取消
            finish();
            Delegate.callbackListener.onError(new WYGameSdkError(ConstData.CANCEL_AUTH, ConstData.CANEL_AUTH_INFO));
        } else if (view.getId() ==ResourceUtil.getViewIdByName(this,"wygamesdk_buttonCreate")) {//确定
            String realName = editName.getText().toString();
            String certificate = editIdNumber.getText().toString();
            if (TextUtils.isEmpty(player_id)) {
                showToast("请登录后再试");
                return;
            }
            if (TextUtils.isEmpty(realName)) {
                showToast("请输入真实姓名");
                return;
            }
            if (TextUtils.isEmpty(certificate)) {
                showToast("请输入您的身份证号");
                return;
            }
            authPresenterImp.realNameAuth(player_id, realName, certificate, this);
        }
    }

    @Override
    public void authResult(int errorCode, String errorMsg) {
        showToast(errorMsg);
        if (errorCode == BaseResponse.SUCCESS) {
            finish();
            UserBean userBean = UserManage.getInstance().getSdkUserInfo(this);
            UserManage.getInstance().saveUserInfo(this, userBean.player_id,
                    userBean.username, userBean.phone,
                    userBean.nickname, userBean.headimgurl,
                    1,userBean.token);
        } else {
            editName.setText("");
            editIdNumber.setText("");
            Delegate.callbackListener.onError(new WYGameSdkError(errorCode, errorMsg));
        }
    }
}
