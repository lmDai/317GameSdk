package com.jiaohe.wygamsdk.mvp.login;

import java.io.Serializable;

/**
 * @package: com.jiaohe.wygamsdk.mvp.login
 * @user:xhkj
 * @date:2019/7/26
 * @description:
 **/
public class UserBean implements Serializable {
    public String id;
    public String player_id;
    public String username;
    public String phone;
    public String nickname;
    public String headimgurl;
    public int is_validate;
    public String token;

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", player_id='" + player_id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", is_validate=" + is_validate +
                ", token='" + token + '\'' +
                '}';
    }
}
