package com.example.appquanly;

public class DonDatHangModel {
    private int MADDH;
    private String NgayLap;

    public DonDatHangModel(int MADDH, String NgayLap) {
        this.MADDH = MADDH;
        this.NgayLap = NgayLap;
    }

    public int getMADDH() {
        return MADDH;
    }

    public void setMADDH(int MADDH) {
        this.MADDH = MADDH;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }
}
