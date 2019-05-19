package com.example.appquanly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LoaiXeAdapterSpinner extends BaseAdapter {
    List<LoaiXeModel> listLoaiXe;
    Context mContext;

    public LoaiXeAdapterSpinner(List<LoaiXeModel> listLoaiXe, Context mContext) {
        this.listLoaiXe = listLoaiXe;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return listLoaiXe.size();
    }

    @Override
    public Object getItem(int position) {
        return listLoaiXe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LoaiXeModel loaiXe = listLoaiXe.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.itemcustom,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.itemName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(loaiXe.getTenLoai());
        return convertView;
    }
    private class ViewHolder{
        TextView tvName;
    }
}
