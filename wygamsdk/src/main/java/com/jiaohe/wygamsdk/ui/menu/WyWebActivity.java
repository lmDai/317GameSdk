package com.jiaohe.wygamsdk.ui.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.widget.MultipleStatusView;

/**
 * webView界面
 */
public class WyWebActivity extends SdkBaseActivity {
    private LinearLayout llClose;
    private MultipleStatusView multipleStatusView;
    private WebView wyWebView;
    private TextView txtWebTitle;
    private ImageButton ibWebBack, ibWenClose;

    @Override
    public int getLayoutId() {
        return R.layout.wygamesdk_web;
    }

    @Override
    public void initViews() {
        llClose = findViewById(R.id.wygamesdk_id_close);
        multipleStatusView = findViewById(R.id.wygamesdk_id_status_view);
        wyWebView = findViewById(R.id.wygamesdk_web_webview);
        txtWebTitle = findViewById(R.id.wygamesdk_web_title);
        ibWebBack = findViewById(R.id.wygamesdk_ib_loading_back);
        ibWenClose = findViewById(R.id.wygamesdk_ib_loading_finish);
        iniWebview();
    }

    @Override
    public void initListener() {
        setOnClick(llClose);
        setOnClick(ibWebBack);
        setOnClick(ibWenClose);
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra("url");
        wyWebView.loadUrl(url);
    }

    @Override
    public boolean
    onKeyDown(int keyCode, KeyEvent event) {
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
            if (wyWebView.canGoBack()) {
                wyWebView.goBack();//返回上个页面
                return;
            } else {
                finish();
            }
        } else if (id == R.id.wygamesdk_ib_loading_finish) {
            finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    private void iniWebview() {
        wyWebView.getSettings().setSupportZoom(false);
        wyWebView.getSettings().setBuiltInZoomControls(false);
        try {
            wyWebView.getSettings().setJavaScriptEnabled(true);
        } catch (Exception e) {
            System.out.println(e);
        }
        wyWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        wyWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            wyWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        wyWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        wyWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        wyWebView.getSettings().setDatabaseEnabled(true);
        //开启 Application Caches 功能
        wyWebView.getSettings().setAppCacheEnabled(false);
        wyWebView.setWebChromeClient(new WebChromeClient());
        wyWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
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
        });
        wyWebView.setDownloadListener(new MWebViewDownLoadListener());
    }

    private class MWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

}
