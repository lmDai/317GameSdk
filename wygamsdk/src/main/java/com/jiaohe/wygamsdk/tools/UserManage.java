package com.jiaohe.wygamsdk.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiaohe.wygamsdk.mvp.login.UserBean;

/**
 * 用户信息的管理类
 * Created by zhang on 2018/8/20
 */
public class UserManage {

    private static UserManage instance;
    public static final String PLAYER_ID = "player_id";
    public static final String USERNAME = "userName";
    public static final String PHONE = "phone";
    public static final String NICKNAME = "nickname";
    public static final String HEADIMGURL = "headimgurl";
    public static final String IS_VALIDATE = "is_validate";
    public static final String PASSWORD = "passWord";
    public static final String ACCOUUT_NAME = "accouut_name ";
    public static final String SP_NAME = "user";
    public static final String CHILDREN_ID = "children_id";
    public static final String TOKEN = "token";

    private UserManage() {
    }

    public static UserManage getInstance() {
        if (instance == null) {
            instance = new UserManage();
        }
        return instance;
    }

    /**
     * 保存自动登录的用户信息
     */
    public void saveUserInfo(Context context, String username, String password) {
        SharedPreferences sp = context.getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public String getLoginUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        return sp.getString(USERNAME, "");
    }

    public String getLoginPassword(Context context) {
        SharedPreferences sp = context.getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE);
        return sp.getString(PASSWORD, "");
    }

    /**
     * 修改密码后置空
     *
     * @param context
     */
    public void clearPass(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(PASSWORD).commit();
    }

    public void saveUserInfo(Context context, String player_id, String username, String phone, String nickname,
                             String headimgurl, int is_validate, String token) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PLAYER_ID, player_id);
        editor.putString(USERNAME, username);
        editor.putString(PHONE, phone);
        editor.putString(NICKNAME, nickname);
        editor.putString(HEADIMGURL, headimgurl);
        editor.putInt(IS_VALIDATE, is_validate);
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getPlayerId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String playerId = sp.getString(PLAYER_ID, "");
        return playerId;
    }

    public UserBean getSdkUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        UserBean userInfo = new UserBean();
        userInfo.player_id = sp.getString(PLAYER_ID, "");
        userInfo.username = sp.getString(USERNAME, "");
        userInfo.phone = sp.getString(PHONE, "");
        userInfo.nickname = sp.getString(NICKNAME, "");
        userInfo.headimgurl = sp.getString(HEADIMGURL, "");
        userInfo.is_validate = sp.getInt(IS_VALIDATE, 0);
        userInfo.token = sp.getString(TOKEN, "");
        return userInfo;
    }

    public void saveChildrenId(Context context, String childrenId) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CHILDREN_ID, childrenId);
        editor.commit();
    }

    public void saveTrumpetName(Context context, String trumpetName) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ACCOUUT_NAME, trumpetName);
        editor.commit();
    }

    public String getTrumpetName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(ACCOUUT_NAME, "");
    }

    /**
     * 登出
     *
     * @param mContext
     * @return
     */
    public boolean loginOut(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        return editor.commit();
    }

    public String getChildrenId(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(CHILDREN_ID, "");
    }
}
