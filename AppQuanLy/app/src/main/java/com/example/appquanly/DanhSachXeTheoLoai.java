package com.example.appquanly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DanhSachXeTheoLoai extends AppCompatActivity {
    List<XeModel> listXeModel;
    ListView lvXe;
    XeAdapter xeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_xe_theo_loai);
        // Anh Xa
        lvXe = findViewById(R.id.lvXe);
       DBManager dbManager = new DBManager(this);
        // Get Data
        Intent intent = getIntent();
        int maLoai = intent.getIntExtra("maLoai",0);
        listXeModel = dbManager.getListXeModelByMaLoai(maLoai);
        if(listXeModel == null){
            listXeModel = new ArrayList<>();
        }
        xeAdapter = new XeAdapter(listXeModel,getApplicationContext());
        lvXe.setAdapter(xeAdapter);
    }
}
