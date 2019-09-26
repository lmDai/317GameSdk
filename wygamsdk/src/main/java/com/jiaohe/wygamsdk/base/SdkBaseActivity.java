package com.jiaohe.wygamsdk.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.tools.ActivityUtils;
/**
 * @package: com.jiaohe.wygamsdk.base
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public abstract class SdkBaseActivity extends Activity implements View.OnClickListener {

    //获取布局id
    public abstract int getLayoutId();

    //初始化view
    public abstract void initViews();

    //初始化事件监听
    public abstract void initListener();

    //初始化数据
    public abstract void initData();

    //处理具体的点击事件
    public abstract void processClick(View view);

    public void onClick(View view) {
        processClick(view);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置横竖屏
        if (ConfigInfo.allowPORTRAIT) {
            //强制为竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //强制为横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (ConfigInfo.touchOUTSIDE) {
            //允许点击窗口外部区域关闭窗口
            this.setFinishOnTouchOutside(true);
        } else {
            //不允许点击窗口外部区域关闭窗口
            this.setFinishOnTouchOutside(false);
        }
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutId());
        initViews();
        initListener();
        initData();
        ActivityUtils.getInstance().attach(this);
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.getInstance().detach(this);
    }
}
