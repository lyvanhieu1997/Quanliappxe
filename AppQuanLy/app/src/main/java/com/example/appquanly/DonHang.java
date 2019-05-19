package com.example.appquanly;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHang extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    DonHangAdapter donHangAdapter;
    List<DonHangModel> listDonHang;
    List<XeModel> listXe;
    Spinner spXe;
    XeModel XeSelect;
    XeAdapterSpinner xeAdapterSpinner;
    DBManager dbManager;
    Button btnThem, btnXoa, btnSua;
    EditText edSoLuong;
    int maCurrentSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang);
        listView = findViewById(R.id.listViewDonHang);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        edSoLuong = findViewById(R.id.edSoLuong);
        spXe = findViewById(R.id.spTenXe);
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
        spXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                XeSelect = listXe.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateView(int position, boolean isDelete){
        if (listDonHang.size() >0){
            DonHangModel DonHang = listDonHang.get(position);
            edSoLuong.setText(String.valueOf(DonHang.getSoLuong()));
            maCurrentSelect = DonHang.getMADDH();
            if(!isDelete){
                listDonHang.get(position).setCheck(!DonHang.isCheck());
            }
            donHangAdapter.notifyDataSetChanged();

        } else {
            edSoLuong.setText("");
            btnSua.setEnabled(false);
        }
    }

    private void intData() {
        dbManager = new DBManager(this);
        listXe = dbManager.getListXe();
        listDonHang = dbManager.getListDonHang();
        if(listXe == null){
            listXe = new ArrayList<>();
        }
        if(listDonHang == null){
            listDonHang = new ArrayList<>();
        }
        donHangAdapter = new DonHangAdapter(listDonHang,this);
        listView.setAdapter(donHangAdapter);
        xeAdapterSpinner = new XeAdapterSpinner(listXe,this);
        spXe.setAdapter(xeAdapterSpinner);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThem:
                if(XeSelect !=null && edSoLuong.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Bạn đang bỏ trống giá trị input", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    ResponseSQL insertDonDatHang = dbManager.insert(dbManager.TABLE_DONDATHANG, new String[]{"ngayLap"},
                            new String[]{simpleDateFormat.format(new Date())});
                    String[] data = new String[]{ String.valueOf(insertDonDatHang.getId()),XeSelect.getMaXe()+"",
                            edSoLuong.getText().toString() };
                    ResponseSQL responseSQL = dbManager.insert(dbManager.TABLE_DONHANG,new String[]{"maDDH","maXe","soLuong"},data);
                    if(responseSQL.isSuccess()){
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        listDonHang.add(new DonHangModel((int)responseSQL.getId(),XeSelect.getMaXe(),
                                Integer.parseInt(edSoLuong.getText().toString()),false));
                        donHangAdapter.notifyDataSetChanged();
                        edSoLuong.setText("");
                        btnSua.setEnabled(true);
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnSua:
                if(XeSelect !=null && edSoLuong.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Bạn đang bỏ trống giá trị input", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    ResponseSQL insertDonDatHang = dbManager.insert(dbManager.TABLE_DONDATHANG, new String[]{"ngayLap"},
                        new String[]{simpleDateFormat.format(new Date())});
                    String[] data = new String[]{ String.valueOf(insertDonDatHang.getId()),XeSelect.getMaXe()+"",
                        edSoLuong.getText().toString() };
                    boolean isSucess = dbManager.update(dbManager.TABLE_DONHANG,new String[]{"maDDH","maXe","soLuong"},data,"maDDH = ?",new String[]{String.valueOf(maCurrentSelect)});
                    if(isSucess){
                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        for(int i = 0;i<listDonHang.size();i++){
                            if(listDonHang.get(i).getMADDH() == maCurrentSelect){
                                DonHangModel donHangModel = new DonHangModel(listDonHang.get(i).getMADDH(),XeSelect.getMaXe(),
                                        Integer.parseInt(edSoLuong.getText().toString()), false);
                                listDonHang.set(i,donHangModel);
                            }
                        }
                        donHangAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnXoa:
                if(listDonHang.size()>0){
                    List<String> listRemove = new ArrayList<>();
                    for(int i = listDonHang.size()-1;i>=0;i--){
                        if(listDonHang.get(i).isCheck()){
                            listRemove.add(String.valueOf(listDonHang.get(i).getMADDH()));
                            listDonHang.remove(i);
                        }
                    }
                    String whereClause = "maDDH IN ("+ new String(new char[listRemove.size()-1]).replace("\0", "?,") + "?)";
                    ResponseSQL responseSQL = dbManager.delete(dbManager.TABLE_DONHANG,whereClause,listRemove.toArray(new String[]{}));
                    if(responseSQL.isSuccess()){
                        Toast.makeText(this, "Xóa thành công ", Toast.LENGTH_SHORT).show();
                        dbManager.delete(dbManager.TABLE_DONHANG,whereClause,listRemove.toArray(new String[]{}));
                    } else {
                        Toast.makeText(this, "Xóa thất bại ", Toast.LENGTH_SHORT).show();
                    }
                    donHangAdapter.notifyDataSetChanged();
                    updateView(0,true);
                }
                break;
        }
    }
}
