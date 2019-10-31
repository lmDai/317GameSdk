package com.jiaohe.wygamsdk.widget;

import android.content.Context;

/**
 * @package: com.jiaohe.wygamsdk.widget
 * @user:xhkj
 * @date:2019/10/31
 * @description:
 **/
public class ResourceUtil {
    public ResourceUtil() {

    }

    public static int getIdByName(Context context, String className, String name) {
        int id = 0;

        try {
            id = context.getResources().getIdentifier(name, className, context.getPackageName());
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return id;
    }
}
