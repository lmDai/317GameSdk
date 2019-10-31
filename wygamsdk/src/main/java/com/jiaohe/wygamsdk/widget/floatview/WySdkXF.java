package com.jiaohe.wygamsdk.widget.floatview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jiaohe.wygamsdk.ui.menu.WyMenuWindowActivity;

/**
 * @package: com.jiaohe.wygamsdk.widget.floatview
 * @user:xhkj
 * @date:2019/10/31
 * @description:
 **/
public class WySdkXF {
    private static Context mContext;
    private static FloatingLayer floatingLayer;
    public static int REQUEST_CODE_SWITCH_ACCOUNT = 500;
    public static int RESULT_CODE_SWITCH_ACCOUNT = 600;

    public WySdkXF() {
    }
    public static void showFloatView(final Activity activity) {
        if (mContext == null) {
            mContext = activity.getApplicationContext();
            floatingLayer = new FloatingLayer(mContext, 0.4f);
            floatingLayer.show(activity);
            floatingLayer.setListener(new FloatingLayer.FloatingLayerListener() {
                public void onShow() {
                    Log.i("single","onSHow");
                }

                public void onClose() {
                    Log.i("single","onClose");
                }

                public void onClick() {
                    WySdkXF.floatingLayer.setXFIcon();
                    activity.startActivityForResult(new Intent(WySdkXF.mContext, WyMenuWindowActivity.class), WySdkXF.REQUEST_CODE_SWITCH_ACCOUNT);
                }
            });
        }

    }

    public static void onDestroy() {
        if (floatingLayer != null) {
            floatingLayer.close();
            mContext = null;
        }

    }
}
