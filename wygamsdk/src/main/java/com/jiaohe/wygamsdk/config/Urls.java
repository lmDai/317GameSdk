package com.jiaohe.wygamsdk.config;

/**
 * @package: com.jiaohe.wygamsdk.config
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class Urls {
    public static final String SERVER = "http://test.api.v1.317youxi.com/";
    //登录
    public static final String URL_LOGIN = SERVER + "sdk/player/login-v2";
    //账号注册
    public static final String URL_ACCOUNT_REGISTER = SERVER + "sdk/player/register";
    //手机号注册验证码
    public static final String URL_PHONE_REGISGER_CODE = SERVER + "sdk/player/send-code";
    //手机号注册
    public static final String URL_PHONE_REGISGER = SERVER + "sdk/player/phone-register";
    //小号列表
    public static final String URL_TRUMPET_LIST = SERVER + "sdk/player/get-children-account";
    //创建小号
    public static final String URL_TRUMPET_CREATE = SERVER + "sdk/player/create-children-account";
    //浮标模块
    public static final String URL_BUOY_INDEX = SERVER + "sdk/buoy/index";
}
