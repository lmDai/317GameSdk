package com.jiaohe.wygamsdk.widget.floatview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.jiaohe.wygamsdk.widget.ResourceUtil;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @package: com.jiaohe.wygamsdk.widget.floatview
 * @user:xhkj
 * @date:2019/10/31
 * @description:
 **/
public class FloatingLayer {
    public boolean ISSHOW = false;
    private WindowManager mWindowManager;
    private LayoutParams mLayoutParams;
    private Context mContext;
    private View mPopView;
    private FloatingLayer.AnimationTimerTask mAnimationTask;
    private Timer mAnimationTimer;
    private FloatingLayer.GetTokenRunnable mGetTokenRunnable;
    private FloatingLayer.FloatingLayerListener mListener;
    private Handler mHander = new Handler();
    private int mWidth;
    private int mHeight;
    private float mPrevX;
    private float mPrevY;
    private int mAnimatonPeriodTime = 16;
    private boolean isMove = false;
    private BigDecimal mStartClickTime;
    private int mStatusBarHeight = 0;

    public FloatingLayer(Context context, float location) {
        this.mContext = context;
        this.initView();
        this.initWindowManager();
        this.initLayoutParams(location);
        this.initStatusBarHeight();
        this.initDrag();
    }

    @SuppressLint({"InflateParams"})
    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mPopView = layoutInflater.inflate(ResourceUtil.getLayoutIdByName(this.mContext,  "wygamesdk_float_layout"), (ViewGroup) null);
    }

    private void initWindowManager() {
        this.mWindowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public void setXFIcon() {
        if (this.mPopView != null) {
            ImageView youxunIv = (ImageView) this.mPopView.findViewById(ResourceUtil.getViewIdByName(this.mContext, "xygame_float_iv"));
            youxunIv.setBackgroundResource(ResourceUtil.getDrawableIdByName(this.mContext,  "wygamesdk_float_icon"));
        }

    }

    private void initStatusBarHeight() {
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            this.mStatusBarHeight = this.mContext.getResources().getDimensionPixelSize(height);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private void initLayoutParams(float location) {
        this.mWidth = this.mContext.getResources().getDisplayMetrics().widthPixels;
        this.mHeight = this.mContext.getResources().getDisplayMetrics().heightPixels;
        this.mLayoutParams = new LayoutParams(-2, -2, 1003, 520, -3);
        this.mLayoutParams.gravity = 51;
        this.mLayoutParams.x = this.mWidth - XYGameSDKUtil.dip2px(this.mContext, 50.0F) / 2;
        this.mLayoutParams.y = (int) ((float) this.mHeight * location);
    }

    private void initDrag() {
        this.mPopView.setOnTouchListener(new OnTouchListener() {
            @SuppressLint({"ClickableViewAccessibility"})
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case 0:
                        FloatingLayer.this.mPrevX = motionEvent.getRawX();
                        FloatingLayer.this.mPrevY = motionEvent.getRawY();
                        FloatingLayer.this.mStartClickTime = BigDecimal.valueOf(System.currentTimeMillis());
                        FloatingLayer.this.isMove = false;
                        break;
                    case 1:
                    case 3:
                        BigDecimal now = BigDecimal.valueOf(System.currentTimeMillis());
                        if (!FloatingLayer.this.isMove && Math.abs(now.subtract(FloatingLayer.this.mStartClickTime).floatValue()) < 500.0F && FloatingLayer.this.mListener != null) {
                            FloatingLayer.this.mListener.onClick();
                        }

                        FloatingLayer.this.mAnimationTimer = new Timer();
                        FloatingLayer.this.mAnimationTask = FloatingLayer.this.new AnimationTimerTask();
                        FloatingLayer.this.mAnimationTimer.schedule(FloatingLayer.this.mAnimationTask, 0L, (long) FloatingLayer.this.mAnimatonPeriodTime);
                        break;
                    case 2:
                        float deltaX = motionEvent.getRawX() - FloatingLayer.this.mPrevX;
                        float deltaY = motionEvent.getRawY() - FloatingLayer.this.mPrevY;
                        LayoutParams var10000 = FloatingLayer.this.mLayoutParams;
                        var10000.x = (int) ((float) var10000.x + deltaX);
                        var10000 = FloatingLayer.this.mLayoutParams;
                        var10000.y = (int) ((float) var10000.y + deltaY);
                        FloatingLayer.this.mPrevX = motionEvent.getRawX();
                        FloatingLayer.this.mPrevY = motionEvent.getRawY();
                        if (FloatingLayer.this.mLayoutParams.x < -FloatingLayer.this.mPopView.getWidth() / 2) {
                            FloatingLayer.this.mLayoutParams.x = -FloatingLayer.this.mPopView.getWidth() / 2;
                        }

                        if (FloatingLayer.this.mLayoutParams.x > FloatingLayer.this.mWidth - FloatingLayer.this.mPopView.getWidth() / 2) {
                            FloatingLayer.this.mLayoutParams.x = FloatingLayer.this.mWidth - FloatingLayer.this.mPopView.getWidth() / 2;
                        }

                        if (FloatingLayer.this.mLayoutParams.y < 0) {
                            FloatingLayer.this.mLayoutParams.y = 0;
                        }

                        if (FloatingLayer.this.mLayoutParams.y > FloatingLayer.this.mHeight - FloatingLayer.this.mPopView.getHeight() - FloatingLayer.this.mStatusBarHeight) {
                            FloatingLayer.this.mLayoutParams.y = FloatingLayer.this.mHeight - FloatingLayer.this.mPopView.getHeight() - FloatingLayer.this.mStatusBarHeight;
                        }

                        try {
                            FloatingLayer.this.mWindowManager.updateViewLayout(FloatingLayer.this.mPopView, FloatingLayer.this.mLayoutParams);
                        } catch (Exception var6) {
                            ;
                        }

                        if (deltaX > 2.0F | deltaY > 2.0F) {
                            FloatingLayer.this.isMove = true;
                        }
                }

                return false;
            }
        });
    }

    public void show(Activity activity) {
        if (!this.ISSHOW) {
            this.ISSHOW = true;
            this.mGetTokenRunnable = new FloatingLayer.GetTokenRunnable(activity);
            this.mHander.postDelayed(this.mGetTokenRunnable, 500L);
        }

    }

    public void close() {
        try {
            if (this.ISSHOW) {
                this.mWindowManager.removeViewImmediate(this.mPopView);
                this.ISSHOW = false;
                if (this.mListener != null) {
                    this.mListener.onClose();
                }
            }
        } catch (Exception var2) {
            ;
        }

    }

    public void setListener(FloatingLayer.FloatingLayerListener listener) {
        this.mListener = listener;
    }

    private class AnimationTimerTask extends TimerTask {
        private int mStepX;
        private int mDestX;

        public AnimationTimerTask() {
            if (FloatingLayer.this.mLayoutParams.x > FloatingLayer.this.mWidth / 2 - FloatingLayer.this.mPopView.getWidth() / 2) {
                this.mDestX = FloatingLayer.this.mWidth - FloatingLayer.this.mPopView.getWidth() / 2;
                this.mStepX = (FloatingLayer.this.mWidth - FloatingLayer.this.mLayoutParams.x - FloatingLayer.this.mPopView.getWidth() / 2) / 10;
            } else {
                this.mDestX = -FloatingLayer.this.mPopView.getWidth() / 2;
                this.mStepX = -((FloatingLayer.this.mLayoutParams.x + FloatingLayer.this.mPopView.getWidth() / 2) / 10);
            }

        }

        public void run() {
            if (Math.abs(this.mDestX - FloatingLayer.this.mLayoutParams.x) <= Math.abs(this.mStepX)) {
                FloatingLayer.this.mLayoutParams.x = this.mDestX;
            } else {
                LayoutParams var10000 = FloatingLayer.this.mLayoutParams;
                var10000.x += this.mStepX;
            }

            try {
                FloatingLayer.this.mHander.post(new Runnable() {
                    public void run() {
                        FloatingLayer.this.mWindowManager.updateViewLayout(FloatingLayer.this.mPopView, FloatingLayer.this.mLayoutParams);
                    }
                });
            } catch (Exception var2) {
                ;
            }

            if (FloatingLayer.this.mLayoutParams.x == this.mDestX) {
                FloatingLayer.this.mAnimationTask.cancel();
                FloatingLayer.this.mAnimationTimer.cancel();
            }

        }
    }

    public interface FloatingLayerListener {
        void onClick();

        void onShow();

        void onClose();
    }

    private class GetTokenRunnable implements Runnable {
        private int count = 0;
        private Activity mActivity;

        public GetTokenRunnable(Activity activity) {
            this.mActivity = activity;
        }

        public void run() {
            if (this.mActivity != null) {
                IBinder token = null;

                try {
                    token = this.mActivity.getWindow().getDecorView().getWindowToken();
                } catch (Exception var3) {

                }

                if (token != null) {
                    try {
                        FloatingLayer.this.mLayoutParams.token = token;
                        FloatingLayer.this.mWindowManager.addView(FloatingLayer.this.mPopView, FloatingLayer.this.mLayoutParams);
                        this.mActivity = null;
                        return;
                    } catch (Exception var4) {
                        ;
                    }
                }

                ++this.count;
                FloatingLayer.this.mLayoutParams.token = null;
                if (this.count < 10 && FloatingLayer.this.mLayoutParams != null) {
                    FloatingLayer.this.mHander.postDelayed(FloatingLayer.this.mGetTokenRunnable, 500L);
                }

            }
        }
    }
}
