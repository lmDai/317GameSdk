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
    //支付获取订单号
    public static final String URL_PAY_ORDER = SERVER + "sdk/pay";
    //支付
    public static final String URL_PAY_GAME = "https://app.317youxi.com/sdkpay/order";
    //忘记密码短信验证码
    public static final String URL_FORGET_PASSWORD_CODE = SERVER + "sdk/sms/send-forget";
    //忘记密码
    public static final String URL_PLAYER_FORGET = SERVER + "sdk/player/forget";
    //实名认证
    public static final String URL_REALNAME_AUTH = SERVER + "sdk/player/realname";
    //获取订单支付状态
    public static final String URL_GET_PAY_RESULT = SERVER + "sdk/pay/get-result";
    //提交角色信息
    public static final String URL_PLAYER_SUBROLE = SERVER + "sdk/player/subrole";
    //用户协议
    public static final String URL_USER_AGREE_MENT = SERVER + "sdk/player/agreement";
    //绑定手机号验证码
    public static final String URL_GET_BIND_CODE = SERVER + "sdk/player/send-code";
    //绑定手机号
    public static final String URL_BIND_PHONE = SERVER + "sdk/player/phone";
}
