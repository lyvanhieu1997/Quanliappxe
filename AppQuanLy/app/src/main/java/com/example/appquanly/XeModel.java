package com.example.appquanly;

public class XeModel {
    private int MaXe;
    private String TenXe;
    private String DungTich;
    private Double DonGia;
    private int MaLoai;
    private boolean isCheck;

    public XeModel(int MaXe, String TenXe, String DungTich, Double DonGia, int MaLoai, boolean isCheck){
        this.MaXe = MaXe;
        this.TenXe = TenXe;
        this.DungTich = DungTich;
        this.DonGia = DonGia;
        this.MaLoai = MaLoai;
        this.isCheck = isCheck;
    }

    public int getMaXe() {
        return MaXe;
    }

    public void setMaXe(int maXe) {
        MaXe = maXe;
    }

    public String getTenXe() {
        return TenXe;
    }

    public void setTenXe(String tenXe) {
        TenXe = tenXe;
    }

    public String getDungTich() {
        return DungTich;
    }

    public void setDungTich(String dungTich) {
        DungTich = dungTich;
    }

    public Double getDonGia() {
        return DonGia;
    }

    public void setDonGia(Double donGia) {
        DonGia = donGia;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }
}
