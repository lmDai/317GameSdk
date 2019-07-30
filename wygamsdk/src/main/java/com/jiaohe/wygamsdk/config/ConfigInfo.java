package com.jiaohe.wygamsdk.config;

import android.content.Context;

/**
 * @package: com.jiaohe.wygamsdk.config
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class ConfigInfo {
    //允许竖屏,默认是横屏
    public static boolean allowPORTRAIT = true;

    //允许在界面以外的位置关闭窗口,默认是不允许
    public static boolean touchOUTSIDE = false;
    //游戏ID
    public static String gameID = "";

    //工会ID
    public static String channelID = "";
    public static Context mContext;
    //用户ID
    public static String userID = "";
}
