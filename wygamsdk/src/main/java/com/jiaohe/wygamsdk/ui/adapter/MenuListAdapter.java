package com.jiaohe.wygamsdk.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.mvp.menu.MenuBean;
import com.jiaohe.wygamsdk.widget.ResourceUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * @package: com.jiaohe.wygamsdk.ui.adapter
 * @user:xhkj
 * @date:2019/7/31
 * @description:
 **/
public class MenuListAdapter extends BaseAdapter {
    private List<MenuBean.ListBean> mList;
    private Context mContext;

    public MenuListAdapter(List<MenuBean.ListBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(ResourceUtil.getLayoutIdByName(mContext,"wygamesdk_item_buoy_index"), null);
            holder.itemImg = convertView.findViewById(ResourceUtil.getViewIdByName(mContext,"wygamesdk_id_item_img"));
            holder.itemTitle = convertView.findViewById(ResourceUtil.getViewIdByName(mContext,"wygamesdk_id_item_title"));
            holder.itemDesc = convertView.findViewById(ResourceUtil.getViewIdByName(mContext,"wygamesdk_id_item_desc"));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.itemTitle.setText(mList.get(position).title);
        holder.itemDesc.setText(mList.get(position).desc);
        OkGo.<Bitmap>get(mList.get(position).image)
                .tag(this)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        holder.itemImg.setImageBitmap(response.body());
                    }
                });

        return convertView;
    }


    class Holder {
        private ImageView itemImg;
        private TextView itemTitle, itemDesc;

    }
}
