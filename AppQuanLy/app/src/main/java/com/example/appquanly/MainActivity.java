package com.example.appquanly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnQLXe, btnLoaiXe, btnDonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnQLXe = findViewById(R.id.btnQLXe);
        btnLoaiXe = findViewById(R.id.btnLoaiXe);
        btnDonHang = findViewById(R.id.btnDonHang);

        btnQLXe.setOnClickListener(this);
        btnDonHang.setOnClickListener(this);
        btnLoaiXe.setOnClickListener(this);
    }

    private void goQlXe(){
        Intent intent = new Intent(this, QuanLyXe.class);
        startActivity(intent);
    }

    private void goLoaiXe(){
        Intent intent = new Intent(this, LoaiXe.class);
        startActivity(intent);
    }

    private void goDonHang(){
        Intent intent = new Intent(this, DonHang.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnQLXe:
                goQlXe();
                break;
            case R.id.btnLoaiXe:
                goLoaiXe();
                break;
            case R.id.btnDonHang:
                goDonHang();
                break;
        }
    }
}
