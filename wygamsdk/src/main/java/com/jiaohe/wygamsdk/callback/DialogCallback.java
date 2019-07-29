package com.jiaohe.wygamsdk.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

import com.lzy.okgo.request.base.Request;

/**
 * @package: com.jiaohe.wygamsdk.callback
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public abstract class DialogCallback<T> extends JsonCallback<T> {
    private ProgressDialog dialog;

    private void initDialog(Context mContext, String msg) {
        dialog = new ProgressDialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
    }

    public DialogCallback(Context mContext, String msg) {
        super();
        initDialog(mContext, msg);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
