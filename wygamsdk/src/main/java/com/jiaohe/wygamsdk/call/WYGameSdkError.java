package com.jiaohe.wygamsdk.call;

/**
 * @package: com.jiaohe.wygamsdk.call
 * @user:xhkj
 * @date:2019/9/29
 * @description:
 **/
public class WYGameSdkError {
    private int errorCode;
    private String errorMessage;

    public WYGameSdkError() {

    }

    public WYGameSdkError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
