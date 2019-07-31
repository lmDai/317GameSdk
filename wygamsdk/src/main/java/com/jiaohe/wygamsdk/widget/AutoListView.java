package com.jiaohe.wygamsdk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @package: com.jiaohe.wygamsdk.widget
 * @user:xhkj
 * @date:2019/7/31
 * @description:
 **/
public class AutoListView extends ListView {
    public AutoListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoListView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
