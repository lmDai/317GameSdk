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

    public void saveUserInfo(Context context, String player_id, String username, String phone, String nickname, String headimgurl, int is_validate) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PLAYER_ID, player_id);
        editor.putString(USERNAME, username);
        editor.putString(PHONE, phone);
        editor.putString(NICKNAME, nickname);
        editor.putString(HEADIMGURL, headimgurl);
        editor.putInt(IS_VALIDATE, is_validate);
        editor.commit();
    }

    public UserBean getSdkUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        UserBean userInfo = new UserBean();
        userInfo.player_id = sp.getString(PLAYER_ID, "");
        userInfo.username = sp.getString(USERNAME, "");
        userInfo.phone = sp.getString(PHONE, "");
        userInfo.nickname = sp.getString(NICKNAME, "");
        userInfo.headimgurl = sp.getString(HEADIMGURL, "");
        userInfo.is_validate = sp.getInt(IS_VALIDATE, 0);
        return userInfo;
    }
}