package com.jiaohe.wygamesdk;

import android.app.Activity;
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
import com.jiaohe.wygamsdk.mvp.login.UserBean;
import com.jiaohe.wygamsdk.widget.floatview.FloatPresentImpl;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btnLogin, btnPay, btnRealName, btnLoginOut, btnIsLogin, btnUserInfo, btnCommitRole;
    public GameSdkLogic gameSdkLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.NoAnimation);
        initViews();
        initData();
    }

    public void initViews() {
        btnLogin = findViewById(R.id.btn_login);
        btnPay = findViewById(R.id.btn_pay);
        btnRealName = findViewById(R.id.btn_realname);
        btnLoginOut = findViewById(R.id.btn_logOut);
        btnIsLogin = findViewById(R.id.btn_is_login);
        btnUserInfo = findViewById(R.id.btn_user_info);
        btnCommitRole = findViewById(R.id.btn_commit_role);
        btnLogin.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        btnRealName.setOnClickListener(this);
        btnLoginOut.setOnClickListener(this);
        btnIsLogin.setOnClickListener(this);
        btnUserInfo.setOnClickListener(this);
        btnCommitRole.setOnClickListener(this);
    }

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
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_login) {
            GameSdkLogic.getInstance().login(new CallbackListener() {
                @Override
                public void onSuccess(Bundle bundle) {//登录成功
                    String channel_accouut_id = bundle.getString("channel_accouut_id");
                    String player_id = bundle.getString("player_id");
                    String accouut_name = bundle.getString("accouut_name");
                    Toast.makeText(MainActivity.this, channel_accouut_id + player_id + accouut_name, Toast.LENGTH_SHORT).show();
                    FloatPresentImpl.getInstance().showFloatBtn(MainActivity.this);
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
            GameSdkLogic.getInstance().wyGamePay(this, thirdId, roleId, roleName, serverId, serverName, gameName, desc, remark, payable, payment, commodity, new CallbackListener() {
                @Override
                public void onSuccess(Bundle bundle) {
                    String errorMsg = bundle.getString("errorMsg");
                    int errorCode = bundle.getInt("errorCode");
                    Toast.makeText(MainActivity.this, errorMsg + errorCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (view.getId() == R.id.btn_realname) {
            GameSdkLogic.getInstance().isRealNameAuth(new CallbackListener() {
                @Override
                public void onSuccess(Bundle bundle) {
                    boolean is_validate = bundle.getBoolean("is_validate");
                    Toast.makeText(MainActivity.this, "是否实名认证：" + is_validate, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (view.getId() == R.id.btn_logOut) {
            GameSdkLogic.getInstance().logout(new CallbackListener() {
                @Override
                public void onSuccess(Bundle bundle) {
                    Toast.makeText(MainActivity.this, "登出成功", Toast.LENGTH_SHORT).show();
                    FloatPresentImpl.getInstance().destoryFloat();
                }

                @Override
                public void onFailed(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (view.getId() == R.id.btn_is_login) {
            GameSdkLogic.getInstance().isLogin(new CallbackListener() {
                @Override
                public void onSuccess(Bundle bundle) {
                    Toast.makeText(MainActivity.this, "是否登录" + bundle.getBoolean("isLogin"), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (view.getId() == R.id.btn_user_info) {
            GameSdkLogic.getInstance().getUserInfo(new CallbackListener() {
                @Override
                public void onSuccess(Bundle bundle) {
                    UserBean userBean = (UserBean) bundle.getSerializable("userInfo");
                    Toast.makeText(MainActivity.this, userBean.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (view.getId() == R.id.btn_commit_role) {
            String roleId = "1010";//角色ID（唯一标识）
            String roleName = "骑小猪看流星";//角色名称
            String roleLevel = "11";//角色等级
            String serverId = "123";//服务器ID（唯一标识）
            String serverName = "齐云楼";//服务器名
            String gameName = "梦幻西游";//游戏名称
            GameSdkLogic.getInstance().subGameInfoMethod(roleId, roleName, roleLevel, serverId, serverName, gameName, new CallbackListener() {
                @Override
                public void onSuccess(Bundle bundle) {
                    String errorMsg = bundle.getString("errorMsg");
                    int errorCode = bundle.getInt("errorCode");
                    Toast.makeText(MainActivity.this, errorMsg + errorCode, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(WYGameSdkError wyGameSdkError) {
                    Toast.makeText(MainActivity.this, wyGameSdkError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
