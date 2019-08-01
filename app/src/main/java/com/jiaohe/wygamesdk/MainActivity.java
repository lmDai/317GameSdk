package com.jiaohe.wygamesdk;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.call.GameSdkLogic;
import com.jiaohe.wygamsdk.callback.SdkCallbackListener;
import com.jiaohe.wygamsdk.config.ConstData;
import com.jiaohe.wygamsdk.config.SDKStatusCode;
import com.jiaohe.wygamsdk.widget.floatview.FloatPresentImpl;

public class MainActivity extends SdkBaseActivity {
    private Button btnLogin, btnPay;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        btnLogin = findViewById(R.id.btn_login);
        btnPay = findViewById(R.id.btn_pay);
    }

    @Override
    public void initListener() {
        setOnClick(btnLogin);
        setOnClick(btnPay);
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
        int id = view.getId();
        if (id == R.id.btn_login) {
            GameSdkLogic.getInstance().sdkLogin(this, new SdkCallbackListener<String>() {
                @Override
                public void callback(int code, String response) {
                    switch (code) {
                        case SDKStatusCode.SUCCESS:
                            FloatPresentImpl.getInstance().showFloatBtn(MainActivity.this);
                            break;
                        case SDKStatusCode.FAILURE:
                            break;
                        case SDKStatusCode.OTHER:
                            break;
                    }
                }
            });
        } else if (id == R.id.btn_pay) {

            String thirdId = "1231jhkaskd23";//第三方订单id（易接订单id）
            String roleId = "1010";//角色ID（唯一标识）
            String roleName = "骑小猪看流星";//角色名称
            String serverId = "123";//服务器ID（唯一标识）
            String serverName = "齐云楼";//服务器名
            String gameName = "梦幻西游";//游戏名称
            String desc = "这是一个正式v1测试支付";//描述
            String remark = "可加入其他信息";//备注（可加入其他信息,玩家看不见）
            String payable = "2";//应付金额
            String payment = "1";//实付金额
            String commodity = "30.5金币";//商品名称
            GameSdkLogic.getInstance().wyGamePay(this, thirdId, roleId, roleName, serverId, serverName, gameName, desc, remark, payable, payment, commodity, new SdkCallbackListener<String>() {
                @Override
                public void callback(int code, String response) {
                    switch (code) {
                        //支付成功：
                        case SDKStatusCode.PAY_SUCCESS:
                            Toast.makeText(MainActivity.this, ConstData.PAY_SUCCESS, Toast.LENGTH_SHORT).show();
                            break;
                        //支付失败：
                        case SDKStatusCode.PAY_FAILURE:
                            Toast.makeText(MainActivity.this, ConstData.PAY_FAILURE, Toast.LENGTH_SHORT).show();
                            break;
                        //支付取消：
                        case SDKStatusCode.PAY_OTHER:
                            Toast.makeText(MainActivity.this, ConstData.PAY_FAILURE, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }

}
