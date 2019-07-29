package com.jiaohe.wygamsdk.mvp;

/**
 * @package: com.jiaohe.wygamsdk.mvp
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class BaseResponse<T> {

    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    public int errorCode;
    public String errorMsg;
    public T data;
}
