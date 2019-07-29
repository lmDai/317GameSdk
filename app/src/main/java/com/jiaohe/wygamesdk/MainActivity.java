package com.jiaohe.wygamesdk;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.call.GameSdkLogic;
import com.jiaohe.wygamsdk.callback.SdkCallbackListener;
import com.jiaohe.wygamsdk.config.SDKStatusCode;

public class MainActivity extends SdkBaseActivity {
    private Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    public void initListener() {
        setOnClick(btnLogin);
    }

    @Override
    public void initData() {
        GameSdkLogic.getInstance().sdkInit(this, new SdkCallbackListener<String>() {
            @Override
            public void callback(int code, String response) {
                Log.i("single", code + response + "callback");
            }
        });
    }

    @Override
    public void processClick(View view) {
        GameSdkLogic.getInstance().sdkLogin(this, new SdkCallbackListener<String>() {
            @Override
            public void callback(int code, String response) {
                Log.i("single", code + response + "callback");
                switch (code) {
                    case SDKStatusCode.SUCCESS:
                        break;
                    case SDKStatusCode.FAILURE:
                        break;
                    case SDKStatusCode.OTHER:
                        break;
                }
            }
        });
    }

}
