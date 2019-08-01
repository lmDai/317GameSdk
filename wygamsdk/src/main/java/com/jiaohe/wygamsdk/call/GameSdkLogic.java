package com.jiaohe.wygamsdk.call;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.jiaohe.wygamsdk.callback.SdkCallbackListener;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.ConstData;
import com.jiaohe.wygamsdk.config.SDKStatusCode;
import com.jiaohe.wygamsdk.ui.login.WyLoginActivity;
import com.jiaohe.wygamsdk.ui.pay.WyWebPayActivity;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @package: com.jiaohe.wygamsdk.call
 * @user:xhkj
 * @date:2019/7/26
 * @description:供接入使用SDK开发人员调用的核心类
 **/
public class GameSdkLogic {
    private boolean checkInit;

    private GameSdkLogic() {
    }

    private volatile static GameSdkLogic sdkLogic;

    public static GameSdkLogic getInstance() {
        if (sdkLogic == null) {
            synchronized (GameSdkLogic.class) {
                if (sdkLogic == null) sdkLogic = new GameSdkLogic();
            }
        }
        return sdkLogic;
    }

    //游戏初始化:
    //这里没有商业接口,固定是初始化成功,实际开发需要根据后台去判断成功/失败
    //只有当初始化的时候才可以进行后续操作
    public void sdkInit(Context context, final SdkCallbackListener<String> callback) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ConfigInfo.mContext = context;
        ConfigInfo.gameID = appInfo.metaData.getString("com.jiaohekeji.appid");
        ConfigInfo.channelID = getChannel(context);
        if (!TextUtils.isEmpty(ConfigInfo.gameID)) {
            callback.callback(SDKStatusCode.SUCCESS, ConstData.INIT_SUCCESS);
            checkInit = true;
        } else {
            callback.callback(SDKStatusCode.FAILURE, ConstData.INIT_FAILURE + "游戏ID为空");
            checkInit = false;
        }
    }

    public String getChannel(Context context) {
        String channel = null;
        final String start_flag = "META-INF/channel_";
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String entryName = entry.getName();
                if (entryName.contains(start_flag)) {
                    channel = entryName.replace(start_flag, "");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (channel == null || channel.length() <= 0) {
            channel = "";
        }
        return channel;
    }

    public void sdkLogin(Context context, final SdkCallbackListener<String> callback) {
        if (checkInit) {
            Intent intent = new Intent(context, WyLoginActivity.class);
            context.startActivity(intent);
            Delegate.listener = callback;
        } else {
            callback.callback(SDKStatusCode.FAILURE, ConstData.INIT_FAILURE);
            return;
        }

    }

    //支付:
    //需要将SDK支付信息传递给具体的方式中
    public void wyGamePay(Context context, String thirdId, String roleId, String roleName, String serverId, String serverName, String gameName, String desc, String remark, String payable, String payment, String commodity, final SdkCallbackListener<String> callback) {
        if (checkInit) {
            Intent intent = new Intent(context, WyWebPayActivity.class);
            Bundle extras = new Bundle();
            extras.putString("thirdId", thirdId);
            extras.putString("roleId", roleId);
            extras.putString("roleName", roleName);
            extras.putString("serverId", serverId);
            extras.putString("serverName", serverName);
            extras.putString("gameName", gameName);
            extras.putString("desc", desc);
            extras.putString("remark", remark);
            extras.putString("payable", payable);
            extras.putString("payment", payment);
            extras.putString("commodity", commodity);
            intent.putExtras(extras);
            context.startActivity(intent);
            Delegate.listener = callback;
        } else {
            callback.callback(SDKStatusCode.FAILURE, ConstData.INIT_FAILURE);
            return;
        }
    }

}
