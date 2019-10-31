package com.jiaohe.wygamsdk.ui.pay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.H5PayCallback;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.H5PayResultModel;
import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.call.Delegate;
import com.jiaohe.wygamsdk.call.WYGameSdkError;
import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.jiaohe.wygamsdk.mvp.pay.OrderBean;
import com.jiaohe.wygamsdk.mvp.pay.PayResultBean;
import com.jiaohe.wygamsdk.tools.UserManage;
import com.jiaohe.wygamsdk.tools.WebViewUtil;
import com.jiaohe.wygamsdk.widget.MultipleStatusView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * webView界面
 */
public class WyWebPayActivity extends SdkBaseActivity {
    private LinearLayout llClose;
    private MultipleStatusView multipleStatusView;
    private WebView wyWebView;
    private TextView txtWebTitle;
    private ImageButton ibWebBack, ibWenClose;
    private String orderId;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void initViews() {
        llClose = findViewById(R.id.wygamesdk_id_close);
        multipleStatusView = findViewById(R.id.wygamesdk_id_status_view);
        wyWebView = findViewById(R.id.wygamesdk_web_webview);
        txtWebTitle = findViewById(R.id.wygamesdk_web_title);
        ibWebBack = findViewById(R.id.wygamesdk_ib_loading_back);
        ibWenClose = findViewById(R.id.wygamesdk_ib_loading_finish);
        WebViewUtil.webSettingsApply(wyWebView.getSettings());
        wyWebView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void initListener() {
        setOnClick(llClose);
        setOnClick(ibWebBack);
        setOnClick(ibWenClose);
    }

    @Override
    public void initData() {
        Bundle extras;
        try {
            extras = getIntent().getExtras();
        } catch (Exception e) {
            finish();
            return;
        }
        if (extras == null) {
            finish();
            return;
        }
        OkGo.<BaseResponse<OrderBean>>post(Urls.URL_PAY_ORDER)//
                .tag(this)
                .params("thirdId", extras.getString("thirdId"))
                .params("roleId", extras.getString("roleId"))
                .params("roleName", extras.getString("roleName"))
                .params("serverId", extras.getString("serverId"))
                .params("serverName", extras.getString("serverName"))
                .params("gameId", ConfigInfo.gameID)
                .params("gameName", extras.getString("gameName"))
                .params("commodity", extras.getString("commodity"))
                .params("payable", extras.getString("payable"))
                .params("payment", extras.getString("payment"))
                .params("desc", extras.getString("desc"))
                .params("remark", extras.getString("remark"))
                .params("userId", ConfigInfo.userID)
                .params("channelId", ConfigInfo.channelID)
                .isMultipart(true)
                .execute(new DialogCallback<BaseResponse<OrderBean>>(this, "数据加载中...") {
                    @Override
                    public void onStart(Request<BaseResponse<OrderBean>, ? extends Request> request) {
                        multipleStatusView.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<BaseResponse<OrderBean>> response) {
                        WyWebPayActivity.this.orderId = response.body().data.id;
                        wyWebView.loadUrl(Urls.URL_PAY_GAME + "?orderId=" + response.body().data.id);
                    }

                    @Override
                    public void onError(Response<BaseResponse<OrderBean>> response) {
                        super.onError(response);
                        multipleStatusView.showError();
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wyWebView.canGoBack()) {
            wyWebView.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);//退出H5界面
    }

    @Override
    public void processClick(View view) {
        int id = view.getId();
        if (id == R.id.wygamesdk_id_close) {//关闭当前页面
            finish();
        } else if (id == R.id.wygamesdk_ib_loading_back) {//返回上一页
            finish();
        } else if (id == R.id.wygamesdk_ib_loading_finish) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebViewUtil.destory(wyWebView);
        getPayResult();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            if (!(url.startsWith("http") || url.startsWith("https"))) {
                return true;
            }
            // 微信支付处理
            if (url.startsWith("weixin://wap/pay?")) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    WyWebPayActivity.this.startActivity(intent);
                    return true;
                } catch (Exception e) { //异常处理
                    view.goBack(); // 因为会出现有一个weixin空白页面；根据需求自己处理
                    showToast("系统检测未安装微信，请先安装微信或者用支付宝支付");
                    return true;
                }
            }
            /**
             * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
             */
            final PayTask task = new PayTask(WyWebPayActivity.this);
            boolean isIntercepted = task.payInterceptorWithUrl(url, true, new H5PayCallback() {
                @Override
                public void onPayResult(final H5PayResultModel result) {
                    // 支付结果返回
                    final String url = result.getReturnUrl();
                    if (!TextUtils.isEmpty(url)) {
                        WyWebPayActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.loadUrl(url);
                            }
                        });
                    }
                }
            });
            /**
             * 判断是否成功拦截
             * 若成功拦截，则无需继续加载该URL；否则继续加载
             */
            if (!isIntercepted || !checkAliPayInstalled(WyWebPayActivity.this)) {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            multipleStatusView.showLoading();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            txtWebTitle.setText(view.getTitle());
            multipleStatusView.showContent();
        }
    }

    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    private void getPayResult() {
        OkGo.<BaseResponse<PayResultBean>>post(Urls.URL_GET_PAY_RESULT)//
                .tag(this)
                .params("children_id", UserManage.getInstance().getChildrenId(this))
                .params("orderId", orderId)
                .params("gameId", ConfigInfo.gameID)
                .params("userId", ConfigInfo.userID)
                .params("channelId", ConfigInfo.channelID)
                .params("client", ConfigInfo.CLIENT)
                .isMultipart(true)
                .execute(new DialogCallback<BaseResponse<PayResultBean>>(this, "数据加载中...") {
                    @Override
                    public void onStart(Request<BaseResponse<PayResultBean>, ? extends Request> request) {
                        multipleStatusView.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<BaseResponse<PayResultBean>> response) {
                        if (response.body().errorCode == BaseResponse.SUCCESS && response.body().data.pay_status == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("errorCode", response.body().errorCode);
                            bundle.putString("errorMsg", response.body().errorMsg);
                            Delegate.callbackListener.onSuccess(bundle);
                        } else {
                            Delegate.callbackListener.onFailed(new WYGameSdkError(response.body().errorCode, response.body().errorMsg));
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<PayResultBean>> response) {
                        super.onError(response);
                        Delegate.callbackListener.onError(new WYGameSdkError(response.code(), response.getException().getMessage()));
                    }
                });
    }
}
