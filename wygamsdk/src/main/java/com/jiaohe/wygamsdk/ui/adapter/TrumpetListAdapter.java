package com.jiaohe.wygamsdk.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiaohe.wygamsdk.R;
import com.jiaohe.wygamsdk.mvp.trumpet.TrumpetBean;

import java.util.List;

/**
 * @package: com.jiaohe.wygamsdk.ui.adapter
 * @user:xhkj
 * @date:2019/7/31
 * @description:
 **/
public class TrumpetListAdapter extends BaseAdapter {
    private List<TrumpetBean> mList;
    private Context mContext;

    public TrumpetListAdapter(List<TrumpetBean> mList, Context mContext) {
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
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.wygamesdk_item_trumpet, null);
            holder.mTextAccountName = convertView.findViewById(R.id.wygamesdk_id_accouut_name);
            holder.mTextPosition = convertView.findViewById(R.id.wygamesdk_id_position);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mTextAccountName.setText(mList.get(position).accouut_name);
        holder.mTextPosition.setText(position + 1 + "");
        return convertView;
    }


    class Holder {

        private TextView mTextAccountName, mTextPosition;

    }
}
