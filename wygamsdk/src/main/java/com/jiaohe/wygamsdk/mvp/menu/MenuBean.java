package com.jiaohe.wygamsdk.mvp.menu;

import java.util.List;

/**
 * @package: com.jiaohe.wygamsdk.mvp.menu
 * @user:xhkj
 * @date:2019/7/31
 * @description:
 **/
public class MenuBean {
    public WalletBean wallet;
    public List<ListBean> list;

    public static class WalletBean {
        public String title;
        public String desc;
        public String image;
        public String jump_type;
        public String link;
        public int screen_type;
    }

    public static class ListBean {
        public String title;
        public String desc;
        public String image;
        public int jump_type;
        public String link;
        public int screen_type;
    }
}
