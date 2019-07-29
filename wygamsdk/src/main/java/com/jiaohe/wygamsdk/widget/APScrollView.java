package com.jiaohe.wygamsdk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @package: com.jiaohe.wygamsdk.widget
 * @user:xhkj
 * @date:2019/7/29
 * @description:
 **/
public class APScrollView extends ScrollView {

    public APScrollView(Context context) {
        super(context);
    }

    public APScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public APScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void fling(int paramInt) {
        super.fling(paramInt / 40);
    }
}
