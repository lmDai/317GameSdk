package com.jiaohe.wygamsdk.widget.floatview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.ui.menu.WyMenuWindowActivity;

class FloatView implements IFloatUI, OnClickListener {
    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;
    private static WindowManager wManager;
    private Activity mActivity;
    private WindowManager.LayoutParams wmParams;
    /**
     * 悬浮窗
     */
    private ImageView floatIV;
    private int screenWidth;
    /**
     * float menu
     */
    private PopupWindow mPopupWindow;
    /**
     * 放置按钮的View
     */
    private CountDownTimer mHideTimer;
    /**
     * 悬浮窗的父控件
     */
    private View mParentView;
    private OnTouchListener touchListener;
    private int LEFT = 0;
    private int RIGHT = 1;
    /**
     * 悬浮窗要隐藏的位置
     */
    private int mHintLocation = LEFT;

    /**
     * 悬浮窗隐藏的距离
     */
    private int length;
    /**
     * float menu的高度
     */
    private int viewHeight = 60;

    FloatView(Activity activity) {
        this.mActivity = activity;
        initView();
    }

    private void initView() {
        initFloatWindowManage();
        initFloat();
        floatBtnEvent();
        initTimer();
    }

    @SuppressWarnings("deprecation")
    private void initFloatWindowManage() {
        wManager = mActivity.getWindowManager();
        screenWidth = wManager.getDefaultDisplay().getWidth();
        int screenHeigth = wManager.getDefaultDisplay().getHeight();
        wmParams = new WindowManager.LayoutParams();
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = screenWidth;
        wmParams.y = screenHeigth / 2;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private void initFloat() {
        viewHeight = XYGameSDKUtil.dip2px(mActivity, viewHeight);//转化成px
        mParentView = LayoutInflater.from(mActivity).inflate(R.layout.wygamesdk_float_layout, null);
        floatIV = mParentView.findViewById(R.id.xygame_float_iv);
        //判断状态栏是否显示 如果不显示则statusBarHeight为0
        WindowManager.LayoutParams attrs = mActivity.getWindow().getAttributes();
        if ((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            statusBarHeight = 0;
        } else {
            statusBarHeight = XYGameSDKUtil.getStatusBarHeight(mActivity);
        }
        wManager.addView(mParentView, wmParams);
    }

    private void initTimer() {
        length = viewHeight / 2;    //隐藏一半
        mHideTimer = new CountDownTimer(3000, 3000) {        //悬浮窗超过5秒没有操作的话会自动隐藏
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {        //隐藏
                if (mHintLocation == LEFT) {
                    TranslateAnimation translateAnimation = new TranslateAnimation(-length, -length, 0, 0);
                    translateAnimation.setDuration(10000);
                    translateAnimation.setFillAfter(true);
                    floatIV.startAnimation(translateAnimation);
                    floatIV.setOnTouchListener(null);//隐藏touch事件
                    wManager.updateViewLayout(mParentView, wmParams);
                } else {
                    TranslateAnimation translateAnimation = new TranslateAnimation(length, length, 0, 0);
                    translateAnimation.setDuration(10000);
                    translateAnimation.setFillAfter(true);
                    floatIV.startAnimation(translateAnimation);
                    floatIV.setOnTouchListener(null);//隐藏touch事件
                    wManager.updateViewLayout(mParentView, wmParams);
                }
            }
        };
    }

    /**
     * 悬浮窗的点击事件
     */
    @SuppressLint("ClickableViewAccessibility")
    private void floatBtnEvent() {
        touchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        floatEventDown(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        floatEventMove(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        floatEventUp();
                        break;
                }
                return true;
            }
        };
        floatIV.setOnTouchListener(touchListener);

        floatIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHintLocation == LEFT) {
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0);
                    translateAnimation.setDuration(10000);
                    translateAnimation.setFillAfter(true);
                    floatIV.startAnimation(translateAnimation);
                    floatIV.setOnTouchListener(touchListener);//隐藏touch事件
                } else {
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0);
                    translateAnimation.setDuration(10000);
                    translateAnimation.setFillAfter(true);
                    floatIV.startAnimation(translateAnimation);
                    floatIV.setOnTouchListener(touchListener);//隐藏touch事件
                }

                wManager.updateViewLayout(mParentView, wmParams);
                floatIV.setOnTouchListener(touchListener);//隐藏touch事件
            }
        });
    }

    private void floatEventDown(MotionEvent event) {
        xInView = event.getX();
        yInView = event.getY();
        xDownInScreen = event.getRawX();
        yDownInScreen = event.getRawY() - statusBarHeight;
        xInScreen = event.getRawX();
        yInScreen = event.getRawY() - statusBarHeight;
        floatIV.setBackgroundResource(R.drawable.wygamesdk_float_icon);
        mHideTimer.cancel();
    }

    private void floatEventMove(MotionEvent event) {
        xInScreen = event.getRawX();
        yInScreen = event.getRawY() - statusBarHeight;
        if (xInScreen != xDownInScreen && yInScreen != yDownInScreen) {
            removeBigWindow();
        }
        wmParams.x = (int) (xInScreen - xInView);
        wmParams.y = (int) (yInScreen - yInView);
        updateViewPosition(); // 手指移动的时候更新小悬浮窗的位置
    }

    private void floatEventUp() {
        if (xInScreen < screenWidth / 2) {   //在左边
            wmParams.x = 0;
            mHintLocation = LEFT;
        } else {                   //在右边
            wmParams.x = screenWidth + 100;
            mHintLocation = RIGHT;
        }
        floatIV.setBackgroundResource(R.drawable.wygamesdk_float_icon);
        mHideTimer.start();
        updateViewPosition();
        if (xInScreen == xDownInScreen && yInScreen == yDownInScreen) {        //点击
//			openMenu();
            ConfigInfo.mContext.startActivity(new Intent(ConfigInfo.mContext, WyMenuWindowActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        wManager.updateViewLayout(mParentView, wmParams);
    }

    /**
     * 移除大悬浮窗
     */
    private void removeBigWindow() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    /**
     * 移除所有悬浮窗
     */
    public void removeAllWindow() {
        try {
            mHideTimer.cancel();
            removeBigWindow();
            mPopupWindow = null;
            wManager.removeViewImmediate(mParentView);
        } catch (Exception e) {
        }
    }

    @Override
    public void destoryFloat() {
        removeAllWindow();
    }
}
