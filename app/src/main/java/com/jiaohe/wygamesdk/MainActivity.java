package com.jiaohe.wygamesdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jiaohe.wygamsdk.base.SdkBaseActivity;
import com.jiaohe.wygamsdk.call.CallbackListener;
import com.jiaohe.wygamsdk.call.GameSdkLogic;
import com.jiaohe.wygamsdk.call.InitCallbackListener;
import com.jiaohe.wygamsdk.call.WYGameSdkError;
import com.jiaohe.wygamsdk.callback.SdkCallbackListener;
import com.jiaohe.wygamsdk.config.ConstData;
import com.jiaohe.wygamsdk.config.SDKStatusCode;

public class MainActivity extends SdkBaseActivity {
    private Button btnLogin, btnPay;
    private GameSdkLogic gameSdkLogic;

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
        GameSdkLogic.sdkInit(this, new InitCallbackListener() {
            @Override
            public void onSuccess() {
                Log.i("single", "onSuccess");
            }

            @Override
            public void onFailed() {
                Log.i("single", "onFailed");
            }
        });
        gameSdkLogic = GameSdkLogic.getInstance();
    }

    @Override
    public void processClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_login) {
            gameSdkLogic.login(new CallbackListener() {
                @Override
                public void onSuccess(Bundle bundle) {//登录成功
                    String channel_accouut_id = bundle.getString("channel_accouut_id");
                    String player_id = bundle.getString("player_id");
                    String accouut_name = bundle.getString("accouut_name");
                    Toast.makeText(MainActivity.this, channel_accouut_id + player_id + accouut_name, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(WYGameSdkError wyGameSdkError) {//登录失败

                }

                @Override
                public void onError(WYGameSdkError wyGameSdkError) {//登录失败

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
