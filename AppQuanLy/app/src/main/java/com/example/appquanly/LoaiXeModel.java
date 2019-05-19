package com.example.appquanly;

import java.io.Serializable;

public class LoaiXeModel implements Serializable {
    private int MaLoai;
    private String TenLoai;
    private String XuatXu;
    private boolean isCheck;

    public LoaiXeModel(int MaLoai, String TenLoai, String XuatXu, Boolean isCheck){
        this.MaLoai = MaLoai;
        this.TenLoai = TenLoai;
        this.XuatXu = XuatXu;
        this.isCheck = isCheck;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String xuatXu) {
        XuatXu = xuatXu;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
