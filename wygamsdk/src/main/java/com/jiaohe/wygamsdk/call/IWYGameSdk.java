package com.jiaohe.wygamsdk.call;

/**
 * @package: com.jiaohe.wygamsdk.call
 * @user:xhkj
 * @date:2019/9/29
 * @description:
 **/
public interface IWYGameSdk {
//    void pay();//支付

    void register(CallbackListener callbackListener);//注册

    void login(CallbackListener callbackListener);//登录

    void logout(CallbackListener callbackListener);//注销

    void isLogin(CallbackListener callbackListener);//是否登录

    void getUserInfo(CallbackListener callbackListener);//获取用户信息

    void isRealNameAuth(CallbackListener callbackListener);//是否实名认证

    void exit(ExitCallbackListener exitCallbackListener);//退出

    void setAccountListener(AccountCallBackListener accountListener);//账号信息监听

//    void createRole();//创建角色
}
