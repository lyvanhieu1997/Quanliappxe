package com.example.appquanly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class XeAdapterSpinner extends BaseAdapter {
    List<XeModel> listXe;
    Context mContext;

    public XeAdapterSpinner(List<XeModel> listXe, Context mContext) {
        this.listXe = listXe;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return listXe.size();
    }

    @Override
    public Object getItem(int position) {
        return listXe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        XeModel xe = listXe.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.itemcustom1,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.itemName1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(xe.getTenXe());
        return convertView;
    }
    private class ViewHolder{
        TextView tvName;
    }
}
