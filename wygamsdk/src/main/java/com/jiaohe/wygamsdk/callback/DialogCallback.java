package com.jiaohe.wygamsdk.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.widget.Toast;

import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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

    @Override
    public void onError(Response<T> response) {
        Throwable exception = response.getException();
        String erroMsg = "";
        if (exception != null) exception.printStackTrace();
        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            erroMsg = "网络连接失败，请连接网络";
        } else if (exception instanceof SocketTimeoutException) {
            erroMsg = "网络请求超时";
        } else if (exception instanceof HttpException) {
            erroMsg = "服务器连接失败";
        } else if (exception instanceof StorageException) {
            erroMsg = "sd卡不存在或者没有权限";
        } else if (exception instanceof IllegalStateException) {
            erroMsg = exception.getMessage();
        }
        Toast.makeText(ConfigInfo.mContext, erroMsg, Toast.LENGTH_SHORT).show();
    }
}
