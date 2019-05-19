package com.example.appquanly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuanLyXe extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    XeAdapter xeAdapter;
    List<XeModel> listXe;
    List<LoaiXeModel> listLoaiXe;
    Spinner spLoaiXe;
    LoaiXeModel loaiXeSelect;
    LoaiXeAdapterSpinner loaiXeAdapterSpinner;
    DBManager dbManager;
    Button btnThem, btnXoa, btnSua, btnThemXe;
    EditText edDonGia, edDungTich, edTenXe;
    int maCurrentSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlyxe);
        listView = findViewById(R.id.listViewXe);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        edDonGia = findViewById(R.id.edDonGia);
        edDungTich = findViewById(R.id.edDungTich);
        edTenXe = findViewById(R.id.edTenXe);
        spLoaiXe = findViewById(R.id.spLoaiXe);
        intData();
        btnSua.setOnClickListener(this);
        btnThem.setOnClickListener(this);
        btnXoa.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateView(position,false);
            }
        });
        spLoaiXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loaiXeSelect = listLoaiXe.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void updateView(int position, boolean isDelete){
        if (listXe.size() >0){
            XeModel Xe = listXe.get(position);
            edTenXe.setText(Xe.getTenXe());
            edDungTich.setText(Xe.getDungTich());
            edDonGia.setText(String.valueOf(Xe.getDonGia()));
            maCurrentSelect = Xe.getMaXe();
            if(!isDelete){
                listXe.get(position).setCheck(!Xe.isCheck());
            }
            xeAdapter.notifyDataSetChanged();

        } else {
            edTenXe.setText("");
            edDungTich.setText("");
            edDonGia.setText("");
            btnSua.setEnabled(false);
        }
    }

    private void intData() {
        dbManager = new DBManager(this);
        listXe = dbManager.getListXe();
        listLoaiXe = dbManager.getlistLoaiXe();
        if(listLoaiXe == null){
            listLoaiXe = new ArrayList<>();
        }
        if(listXe == null){
            listXe = new ArrayList<>();
        }
        xeAdapter = new XeAdapter(listXe,this);
        listView.setAdapter(xeAdapter);
        loaiXeAdapterSpinner = new LoaiXeAdapterSpinner(listLoaiXe,this);
        spLoaiXe.setAdapter(loaiXeAdapterSpinner);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThem:
                if(edTenXe.getText().toString().isEmpty() && edDungTich.getText().toString().isEmpty()
                && edDonGia.getText().toString().isEmpty() && loaiXeSelect !=null )
                {
                    Toast.makeText(this, "Bạn đang bỏ trống giá trị input", Toast.LENGTH_SHORT).show();
                } else {
                    String[] data = new String[]{edTenXe.getText().toString(), edDungTich.getText().toString(), edDonGia.getText().toString(),
                    loaiXeSelect.getMaLoai()+""};
                    ResponseSQL responseSQL = dbManager.insert(dbManager.TABLE_QUANLYXE,new String[]{"tenXe","dungTich","donGia","maLoai"},data);
                    if(responseSQL.isSuccess()){
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        listXe.add(new XeModel((int)responseSQL.getId(),edTenXe.getText().toString(),edDungTich.getText().toString(),
                                Double.parseDouble(edDonGia.getText().toString()), loaiXeSelect.getMaLoai(),false));
                        xeAdapter.notifyDataSetChanged();
                        edTenXe.setText("");
                        edDungTich.setText("");
                        edDonGia.setText("");
                        btnSua.setEnabled(true);
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnSua:
                if(edTenXe.getText().toString().isEmpty() && edDungTich.getText().toString().isEmpty() &&
                        edDonGia.getText().toString().isEmpty() && loaiXeSelect.getTenLoai().isEmpty() )
                {
                    Toast.makeText(this, "Bạn đang bỏ trống giá trị input", Toast.LENGTH_SHORT).show();
                } else {
                    String[] data = new String[]{edTenXe.getText().toString(), edDungTich.getText().toString(), edDonGia.getText().toString(),
                            loaiXeSelect.getTenLoai()};
                    boolean isSucess = dbManager.update(dbManager.TABLE_QUANLYXE,new String[]{"tenXe","dungTich","donGia"},data,"maXe = ?",new String[]{String.valueOf(maCurrentSelect)});
                    if(isSucess){
                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        for(int i = 0;i<listXe.size();i++){
                            if(listXe.get(i).getMaXe() == maCurrentSelect){
                                XeModel XeModel = new XeModel(listXe.get(i).getMaXe(),edTenXe.getText().toString(),edDungTich.getText().toString(),
                                        Double.parseDouble(edDonGia.getText().toString()), loaiXeSelect.getMaLoai(),false);
                                listXe.set(i,XeModel);
                            }
                        }
                        xeAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnXoa:
                if(listXe.size()>0){
                    List<String> listRemove = new ArrayList<>();
                    for(int i = listXe.size()-1;i>=0;i--){
                            if(listXe.get(i).isCheck()){
                                listRemove.add(String.valueOf(listXe.get(i).getMaXe()));
                                listXe.remove(i);
                            }
                    }
                    String whereClause = "maXe IN ("+ new String(new char[listRemove.size()-1]).replace("\0", "?,") + "?)";
                    ResponseSQL responseSQL = dbManager.delete(dbManager.TABLE_QUANLYXE,whereClause,listRemove.toArray(new String[]{}));
                    if(responseSQL.isSuccess()){
                        Toast.makeText(this, "Xóa thành công ", Toast.LENGTH_SHORT).show();
                        dbManager.delete(dbManager.TABLE_QUANLYXE,whereClause,listRemove.toArray(new String[]{}));
                    } else {
                        Toast.makeText(this, "Xóa thất bại ", Toast.LENGTH_SHORT).show();
                    }
                    xeAdapter.notifyDataSetChanged();
                    updateView(0,true);
                }
                break;
        }
    }
}