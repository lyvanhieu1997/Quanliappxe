package com.example.appquanly;

public class DonHangModel {
    private int MADDH;
    private int MaXe;
    private int SoLuong;
    private boolean isCheck;

    public DonHangModel(int MADDH, int MaXe, int SoLuong, Boolean isCheck){
        this.MADDH = MADDH;
        this.MaXe = MaXe;
        this.SoLuong = SoLuong;
        this.isCheck = isCheck;
    }

    public int getMADDH() {
        return MADDH;
    }

    public void setMADDH(int MADDH) {
        this.MADDH = MADDH;
    }

    public int getMaXe() {
        return MaXe;
    }

    public void setMaXe(int maXe) {
        MaXe = maXe;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
