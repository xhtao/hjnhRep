package com.xht.cmsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by XIE on 2018/7/31.
 */

public class BtnLvAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater = null;
    private List<BtnLvItemData> mData = null;

    public BtnLvAdapter(Context context, List<BtnLvItemData> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null){
            convertView = layoutInflater.inflate(R.layout.adapter_btn_lv, parent, false);

            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_name);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        BtnLvItemData itemData = mData.get(position);
        holder.icon.setImageResource(itemData.getBtn_icon());
        holder.textView.setText(itemData.getBtn_name());

        return convertView;
    }

    private class ViewHolder{
        ImageView   icon;
        TextView    textView;
    }
}
