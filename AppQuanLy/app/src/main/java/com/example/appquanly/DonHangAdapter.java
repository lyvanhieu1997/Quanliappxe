package com.example.appquanly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DonHangAdapter extends BaseAdapter {

    List<DonHangModel> listDonHang;
    Context mContext;
    DBManager dbManager;


    public DonHangAdapter(List<DonHangModel> listDonHang, Context mContext){
        this.listDonHang = listDonHang;
        this.mContext = mContext;
        dbManager = new DBManager(mContext);
    }

    @Override
    public int getCount(){
        return listDonHang.size();
    }

    @Override
    public Object getItem(int position){
        return listDonHang.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_donhang,parent,false );
            viewHolder = new ViewHolder();
            viewHolder.tvCheck = convertView.findViewById(R.id.isCheck);
            viewHolder.tvXe = convertView.findViewById(R.id.tvTenXe);
            viewHolder.tvSoLuong = convertView.findViewById(R.id.tvSoLuong);
            viewHolder.tvDonDatHang = convertView.findViewById(R.id.tvNgayLap);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DonHangModel DonHangModel = listDonHang.get(position);
        if(DonHangModel.isCheck()){
            viewHolder.tvCheck.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvCheck.setVisibility(View.GONE);
        }
        DonHangModel donHangModel = listDonHang.get(position);
        viewHolder.tvSoLuong.setText(String.valueOf(donHangModel.getSoLuong()));
        DonDatHangModel donDatHangModel = dbManager.findDonDatHangByID(donHangModel.getMADDH());
        viewHolder.tvDonDatHang.setText(donDatHangModel.getNgayLap());
        XeModel xeModel = dbManager.findXeByID(donHangModel.getMaXe());
        viewHolder.tvXe.setText(xeModel.getTenXe());
        return convertView;

    }
    private class ViewHolder{
        TextView tvCheck, tvSoLuong, tvXe, tvDonDatHang;
    }
}
