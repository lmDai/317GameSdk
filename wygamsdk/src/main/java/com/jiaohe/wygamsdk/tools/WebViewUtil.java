package com.jiaohe.wygamsdk.tools;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @package: com.jiaohe.wygamsdk.tools
 * @user:xhkj
 * @date:2019/8/1
 * @description:
 **/
public final class WebViewUtil {
    /**
     * 应用WebView的设置
     * <ul>
     * <li>webSettings.setDomStorageEnabled(true);//设置DOM Storage缓存</li>
     * <li>webSettings.setDatabaseEnabled(true);//设置可使用数据库</li>
     * <li>webSettings.setJavaScriptEnabled(true);//支持js脚本</li>
     * <li>webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小</li>
     * <li>webSettings.setSupportZoom(false);//支持缩放</li>
     * <li>webSettings.setBuiltInZoomControls(false);//支持缩放</li>
     * <li>webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//支持内容从新布局</li>
     * <li>webSettings.setSupportMultipleWindows(false);//多窗口</li>
     * <li>webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//关闭webview中缓存</li>
     * <li>webSettings.setAllowFileAccess(true);//设置可以访问文件</li>
     * <li>webSettings.setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点</li>
     * <li>webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口</li>
     * <li>webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片</li>
     * <li>webSettings.setGeolocationEnabled(true);//启用地理定位</li>
     * <li>webSettings.setAllowFileAccessFromFileURLs(true);//使用允许访问文件的urls</li>
     * <li>webSettings.setAllowUniversalAccessFromFileURLs(true);//使用允许访问文件的urls</li>
     * </ul>
     */
    @SuppressLint("SetJavaScriptEnabled")
    public static void webSettingsApply(WebSettings webSettings) {
        webSettings.setDomStorageEnabled(true);//设置DOM Storage缓存
        webSettings.setDatabaseEnabled(true);//设置可使用数据库
        webSettings.setJavaScriptEnabled(true);//支持js脚本
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSettings.setSupportZoom(false);//支持缩放
        webSettings.setBuiltInZoomControls(false);//支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//支持内容从新布局
        webSettings.setSupportMultipleWindows(false);//多窗口
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//关闭webview中缓存
        webSettings.setAllowFileAccess(true);//设置可以访问文件
        webSettings.setNeedInitialFocus(true);//当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片N
        webSettings.setGeolocationEnabled(true);//启用地理定位

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);//使用允许访问文件的urls
            webSettings.setAllowUniversalAccessFromFileURLs(true);//使用允许访问文件的urls
        }

        // webSettings.setRenderPriority(WebSettings.RenderPriority.NORMAL);//设置渲染优先级 will be Deprecated
    }

    /**
     * webView 销毁webView避免内存泄漏
     */
    public static  void destory(WebView webView){
        if(webView != null){
            webView.stopLoading();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
        }
    }
}
