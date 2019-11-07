package com.jiaohe.wygamsdk.call;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.jiaohe.wygamsdk.callback.DialogCallback;
import com.jiaohe.wygamsdk.config.ConfigInfo;
import com.jiaohe.wygamsdk.config.ConstData;
import com.jiaohe.wygamsdk.config.SDKStatusCode;
import com.jiaohe.wygamsdk.config.Urls;
import com.jiaohe.wygamsdk.mvp.BaseResponse;
import com.jiaohe.wygamsdk.tools.UserManage;
import com.jiaohe.wygamsdk.ui.auth.WyRealNameAuthActivity;
import com.jiaohe.wygamsdk.ui.login.WyLoginActivity;
import com.jiaohe.wygamsdk.ui.pay.WyWebPayActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import okhttp3.OkHttpClient;

/**
 * @package: com.jiaohe.wygamsdk.call
 * @user:xhkj
 * @date:2019/7/26
 * @description:供接入使用SDK开发人员调用的核心类
 **/
public class GameSdkLogic implements IWYGameSdk {
    private boolean checkInit;

    private static GameSdkLogic gameSdkLogic;
    private Context mContext;

    private GameSdkLogic(Context context, final InitCallbackListener listener) {
        this.mContext = context;
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
            listener.onSuccess();
            checkInit = true;
        } else {
            listener.onFailed();
            checkInit = false;
        }
    }

    public static GameSdkLogic getInstance() {
        return gameSdkLogic;
    }

    //游戏初始化:
    public static GameSdkLogic sdkInit(Context context, final InitCallbackListener initCallbackListener) {
        if (gameSdkLogic == null) {
            gameSdkLogic = new GameSdkLogic(context, initCallbackListener);
        }
        return gameSdkLogic;
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

    //支付:
    //需要将SDK支付信息传递给具体的方式中
    public void wyGamePay(Context context, String thirdId, String roleId, String roleName, String serverId, String serverName, String gameName, String desc, String remark, String payable, String payment, String commodity, final CallbackListener callback) {
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
            Delegate.callbackListener = callback;
        } else {
            callback.onFailed(new WYGameSdkError(SDKStatusCode.FAILURE, ConstData.INIT_FAILURE));
            return;
        }
    }

    public static void initOkGo(Application application) {
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(application)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        // 其他统一的配置
        OkGo.getInstance().init(application)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数
    }

    @Override
    public void register(CallbackListener callbackListener) {

    }

    @Override
    public void login(CallbackListener callbackListener) {
        if (checkInit) {
            Intent intent = new Intent(mContext, WyLoginActivity.class);
            mContext.startActivity(intent);
            Delegate.callbackListener = callbackListener;
        } else {
            callbackListener.onError(new WYGameSdkError(SDKStatusCode.FAILURE, ConstData.INIT_FAILURE));
            return;
        }
    }

    @Override
    public void logout(CallbackListener callbackListener) {
        boolean isLoginOut = UserManage.getInstance().loginOut(mContext);
        if (isLoginOut) {
            callbackListener.onSuccess(null);
        } else {
            callbackListener.onFailed(new WYGameSdkError(ConstData.LOGIN_OUT, ConstData.LOGINT_OUT_INFO));
        }
    }

    @Override
    public void isLogin(CallbackListener callbackListener) {
        if (TextUtils.isEmpty(UserManage.getInstance().getPlayerId(mContext))) {
            callbackListener.onFailed(new WYGameSdkError(ConstData.NOT_LOGIN, ConstData.NOT_LOGIN_INFO));
        } else {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isLogin", !TextUtils.isEmpty(UserManage.getInstance().getPlayerId(mContext)));
            callbackListener.onSuccess(bundle);
        }
    }

    @Override
    public void getUserInfo(CallbackListener callbackListener) {
        if (TextUtils.isEmpty(UserManage.getInstance().getPlayerId(mContext))) {
            callbackListener.onFailed(new WYGameSdkError(ConstData.NOT_LOGIN, ConstData.NOT_LOGIN_INFO));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("userInfo", UserManage.getInstance().getSdkUserInfo(mContext));
        callbackListener.onSuccess(bundle);
    }

    @Override
    public void isRealNameAuth(CallbackListener callbackListener) {
        if (TextUtils.isEmpty(UserManage.getInstance().getPlayerId(mContext))) {
            callbackListener.onFailed(new WYGameSdkError(ConstData.NOT_LOGIN, ConstData.NOT_LOGIN_INFO));
            return;
        }
        if (UserManage.getInstance().getSdkUserInfo(mContext).is_validate == 1) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_validate", UserManage.getInstance().getSdkUserInfo(mContext).is_validate == 1);
            callbackListener.onSuccess(bundle);
            return;
        }
        //实名认证
        Intent intent = new Intent(mContext, WyRealNameAuthActivity.class);
        mContext.startActivity(intent);
        Delegate.callbackListener = callbackListener;
    }

    @Override
    public void exit(ExitCallbackListener exitCallbackListener) {

    }

    @Override
    public void setAccountListener(AccountCallBackListener accountListener) {

    }

    @Override
    public void subGameInfoMethod(String roleId, String roleName, String roleLevel, String serverId, String serverName, String gameName, final CallbackListener callbackListener) {

        OkGo.<BaseResponse>post(Urls.URL_PLAYER_SUBROLE)//
                .tag(this)
                .params("roleId", roleId)
                .params("roleName", roleName)
                .params("roleLevel", roleLevel)
                .params("serverId", serverId)
                .params("serverName", serverName)
                .params("gameName", gameName)
                .params("gameId", ConfigInfo.gameID)
                .params("channelId", ConfigInfo.channelID)
                .params("userId", ConfigInfo.userID)
                .params("children_id", UserManage.getInstance().getChildrenId(mContext))
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .execute(new DialogCallback<BaseResponse>(mContext, "加载中...") {
                    @Override
                    public void onSuccess(Response<BaseResponse> response) {
                        if (response.body().errorCode == BaseResponse.SUCCESS) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("errorCode", response.body().errorCode);
                            bundle.putString("errorMsg", response.body().errorMsg);
                            callbackListener.onSuccess(bundle);
                        } else {
                            callbackListener.onFailed(new WYGameSdkError(response.body().errorCode, response.body().errorMsg));
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse> response) {
                        super.onError(response);
                        callbackListener.onError(new WYGameSdkError(response.code(), response.getException().getMessage()));
                    }
                });
    }

}
