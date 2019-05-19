package com.example.appquanly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class XeAdapter extends BaseAdapter {
    List<XeModel> listXe;
    DBManager dbManager;

    Context mContext;

    public XeAdapter(List<XeModel> listXe, Context mContext){
        this.listXe = listXe;
        this.mContext = mContext;
        dbManager = new DBManager(mContext);
    }

    @Override
    public int getCount(){
        return listXe.size();
    }

    @Override
    public Object getItem(int position){
        return listXe.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_xe,parent,false );
            viewHolder = new ViewHolder();
            viewHolder.tvCheck = convertView.findViewById(R.id.isCheck);
            viewHolder.tvTenXe = convertView.findViewById(R.id.tvTenXe);
            viewHolder.tvDungTich = convertView.findViewById(R.id.tvDungTich);
            viewHolder.tvDonGia = convertView.findViewById(R.id.tvDonGia);
            viewHolder.tvLoaiXeModel = convertView.findViewById(R.id.tvTenLoai);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        XeModel XeModel = listXe.get(position);
        if(XeModel.isCheck()){
            viewHolder.tvCheck.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvCheck.setVisibility(View.GONE);
        }
        XeModel xeModel = listXe.get(position);
        viewHolder.tvTenXe.setText(xeModel.getTenXe());
        viewHolder.tvDungTich.setText(xeModel.getDungTich());
        viewHolder.tvDonGia.setText(String.valueOf(xeModel.getDonGia()));
        LoaiXeModel loaiXeModel = dbManager.findLoaiXeByID(xeModel.getMaLoai());
        viewHolder.tvLoaiXeModel.setText(loaiXeModel.getTenLoai());
        return convertView;
    }
    private class ViewHolder{
        TextView tvLoaiXeModel,tvTenXe,tvDungTich,tvDonGia,tvCheck;
    }
}
