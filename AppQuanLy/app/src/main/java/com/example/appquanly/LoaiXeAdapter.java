package com.example.appquanly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.appquanly.LoaiXeModel;

import java.util.List;

public class LoaiXeAdapter extends BaseAdapter {
    List<LoaiXeModel> listLoaiXe;
    Context mContext;

    public LoaiXeAdapter(List<LoaiXeModel> listLoaiXe, Context mContext){
        this.listLoaiXe = listLoaiXe;
        this.mContext = mContext;
    }

    @Override
    public int getCount(){
        return listLoaiXe.size();
    }

    @Override
    public Object getItem(int position){
        return listLoaiXe.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
       ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_loaixe,parent,false );
            viewHolder = new ViewHolder();
            viewHolder.tvCheck = convertView.findViewById(R.id.isCheck);
            viewHolder.tvTenLoai = convertView.findViewById(R.id.tvTenLoai);
            viewHolder.tvXuatXu = convertView.findViewById(R.id.tvXuatXu);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiXeModel loaiXeModel = listLoaiXe.get(position);
        if(loaiXeModel.isCheck()){
            viewHolder.tvCheck.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvCheck.setVisibility(View.GONE);
        }
        viewHolder.tvTenLoai.setText(loaiXeModel.getTenLoai());
        viewHolder.tvXuatXu.setText(loaiXeModel.getXuatXu());

        return convertView;

    }
    private class ViewHolder{
        TextView tvCheck,tvTenLoai,tvXuatXu;
    }
}