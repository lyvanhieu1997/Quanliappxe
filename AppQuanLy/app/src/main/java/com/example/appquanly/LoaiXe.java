package com.example.appquanly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoaiXe extends AppCompatActivity implements View.OnClickListener {
    List<LoaiXeModel> listLoaiXe;
    LoaiXeAdapter loaiXeAdapter;
    DBManager dbManager;
    ListView listView;
    Button btnThem, btnXoa, btnSua, btnThemXe;
    EditText edTenloai, edXuatXu;
    int maCurrentSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaixe);

        listView = findViewById(R.id.listViewLoaiXe);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        btnThemXe = findViewById(R.id.btnThemXe);
        edTenloai= findViewById(R.id.edTenLoai);
        edXuatXu = findViewById(R.id.edXuatXu);
        dbManager = new DBManager(this);
        initData();
        btnThemXe.setOnClickListener(this);
        btnThem.setOnClickListener(this);
        btnSua.setOnClickListener(this);
        btnXoa.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateView(position,false);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LoaiXeModel loaiXeModel = listLoaiXe.get(position);
                Intent intent = new Intent(getApplicationContext(),DanhSachXeTheoLoai.class);
                intent.putExtra("maLoai",loaiXeModel.getMaLoai());
                startActivity(intent);
                return false;
            }
        });

    }

    private void goThemXe(){
        Intent intent = new Intent(this, QuanLyXe.class);
        startActivity(intent);
    }

    private void updateView(int position, boolean isDelete){
        if (listLoaiXe.size() >0){
            LoaiXeModel loaiXe = listLoaiXe.get(position);
            edTenloai.setText(loaiXe.getTenLoai());
            edXuatXu.setText(loaiXe.getXuatXu());
            maCurrentSelect = loaiXe.getMaLoai();
            if(!isDelete){
                listLoaiXe.get(position).setCheck(!loaiXe.isCheck());
            }
            loaiXeAdapter.notifyDataSetChanged();

        } else {
            edTenloai.setText("");
            edXuatXu.setText("");
            btnSua.setEnabled(false);
        }
    }

    private void initData() {
        listLoaiXe = dbManager.getlistLoaiXe();
        if(listLoaiXe == null){
            listLoaiXe = new ArrayList<>();
        }
        loaiXeAdapter = new LoaiXeAdapter(listLoaiXe,this);
        listView.setAdapter(loaiXeAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThem:
                if(edTenloai.getText().toString().isEmpty() || edXuatXu.getText().toString().isEmpty()){
                    Toast.makeText(this, "Bạn đang bỏ trống giá trị input", Toast.LENGTH_SHORT).show();
                } else {
                    String[] data = new String[]{edTenloai.getText().toString(), edXuatXu.getText().toString()};
                    ResponseSQL responseSQL = dbManager.insert(dbManager.TABLE_LOAIXE,new String[]{"tenLoai","xuatXu"},data);
                    if(responseSQL.isSuccess()){
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        listLoaiXe.add(new LoaiXeModel((int)responseSQL.getId(),edTenloai.getText().toString(),edXuatXu.getText().toString(),false));
                        loaiXeAdapter.notifyDataSetChanged();
                        edTenloai.setText("");
                        edXuatXu.setText("");
                        btnSua.setEnabled(true);
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnSua:
                if(edTenloai.getText().toString().isEmpty() || edXuatXu.getText().toString().isEmpty()){
                    Toast.makeText(this, "Bạn đang bỏ trống giá trị input", Toast.LENGTH_SHORT).show();
                } else {
                    String[] data = new String[]{edTenloai.getText().toString(), edXuatXu.getText().toString()};
                    boolean isSucess = dbManager.update(dbManager.TABLE_LOAIXE,new String[]{"tenLoai","xuatXu"},data,"maLoai = ?",new String[]{String.valueOf(maCurrentSelect)});
                    if(isSucess){
                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        for(int i = 0;i<listLoaiXe.size();i++){
                            if(listLoaiXe.get(i).getMaLoai() == maCurrentSelect){
                                LoaiXeModel loaiXeModel = new LoaiXeModel(listLoaiXe.get(i).getMaLoai(),edTenloai.getText().toString(),edXuatXu.getText().toString(),false);
                                listLoaiXe.set(i,loaiXeModel);
                            }
                        }
                        loaiXeAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnXoa:
                if(listLoaiXe.size()>0){
                    List<String> listRemove = new ArrayList<>();
                    for(int i = listLoaiXe.size()-1;i>=0;i--){
                        if(listLoaiXe.get(i).isCheck()){
                            listRemove.add(String.valueOf(listLoaiXe.get(i).getMaLoai()));
                            listLoaiXe.remove(i);
                        }
                    }
                    String whereClause = "maLoai IN ("+ new String(new char[listRemove.size()-1]).replace("\0", "?,") + "?)";
                    ResponseSQL responseSQL = dbManager.delete(dbManager.TABLE_LOAIXE,whereClause,listRemove.toArray(new String[]{}));
                    if(responseSQL.isSuccess()){
                        Toast.makeText(this, "Xóa thành công ", Toast.LENGTH_SHORT).show();
                        dbManager.delete(dbManager.TABLE_QUANLYXE,whereClause,listRemove.toArray(new String[]{}));
                    } else {
                        Toast.makeText(this, "Xóa thất bại ", Toast.LENGTH_SHORT).show();
                    }
                    loaiXeAdapter.notifyDataSetChanged();
                    updateView(0,true);
                }
                break;
            case R.id.btnThemXe:
                goThemXe();
                break;

        }
    }

}
