package com.example.appquanly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String DB_PATH = Environment.getDataDirectory()
                                        + "/data/com.example.appquanly/database";
    private static final String DB_NAME = "quanlyxe.db";
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    // Table SQLite
    public static String TABLE_QUANLYXE = "xe";
    public static String TABLE_LOAIXE = "loaixe";
    public static String TABLE_DONHANG = "donhang";
    public static String TABLE_DONDATHANG = "dondathang";
    // Query
    private static String GET_ALL_QUAN_LY_XE = "SELECT * FROM " + TABLE_QUANLYXE;
    private static String GET_ALL_LOAI_XE = "SELECT * FROM " + TABLE_LOAIXE;
    private static String GET_ALL_DON_HANG = "SELECT * FROM " + TABLE_DONHANG;
    private static String GET_ALL_DON_DAT_HANG = "SELECT * FROM " + TABLE_DONDATHANG;

    public DBManager(Context mContext) {
        this.mContext = mContext;
        copyDataBase();
    }

    private void copyDataBase() {
        new File(DB_PATH).mkdir();
        File file = new File(DB_PATH +"/" +DB_NAME);
        try {
            if(file.exists()){
               // Toast.makeText(mContext, "Copy exist", Toast.LENGTH_SHORT).show();
                return;
            }
            InputStream inputStream = mContext.getAssets().open(DB_NAME);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int len;
            byte buff[] = new byte[1024];
            while ((len = inputStream.read(buff))>0){
                fileOutputStream.write(buff,0,len);
            }
            fileOutputStream.close();
            inputStream.close();
            //Toast.makeText(mContext, "Copy file done", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openDataBase(){
        if((mSQLiteDatabase == null) || !mSQLiteDatabase.isOpen()){
            mSQLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH + "/" + DB_NAME,null,SQLiteDatabase.OPEN_READWRITE);
        }
    }
    private void closeDataBase(){
        if((mSQLiteDatabase != null) || mSQLiteDatabase.isOpen()){
          mSQLiteDatabase.close();
        }
    }

    public List<XeModel> getListXe(){
        List<XeModel> listXe = new ArrayList<>();
        openDataBase();
        Cursor cursor = mSQLiteDatabase.rawQuery(GET_ALL_QUAN_LY_XE,null);
        if(cursor == null){
            return null;
        }
        int indexMaXe = cursor.getColumnIndex("maXe");
        int indexTenXe = cursor.getColumnIndex("tenXe");
        int indexDungTich = cursor.getColumnIndex("dungTich");
        int indexDonGia = cursor.getColumnIndex("donGia");
        int indexMaLoai = cursor.getColumnIndex("maLoai");
        cursor.moveToFirst();
        int maXe, maLoai;
        String tenXe,dungTich;
        Double donGia;
        while (!cursor.isAfterLast()){
             maXe = Integer.parseInt(cursor.getString(indexMaXe));
             tenXe = cursor.getString(indexTenXe);
             dungTich = cursor.getString(indexDungTich);
             donGia = Double.parseDouble(cursor.getString(indexDonGia));
             maLoai = Integer.parseInt(cursor.getString(indexMaLoai));
             listXe.add(new XeModel
                     (maXe,tenXe,dungTich,donGia,maLoai, false));
            cursor.moveToNext();
        }
        cursor.close();
        closeDataBase();
        return listXe;
    }

    public List<LoaiXeModel> getlistLoaiXe() {
        List<LoaiXeModel> listLoaiXe = new ArrayList<>();
        openDataBase();
        Cursor cursor = mSQLiteDatabase.rawQuery(GET_ALL_LOAI_XE,null);
        if(cursor == null){
            return null;
        }
        int indexMaLoai = cursor.getColumnIndex("maLoai");
        int indexTenLoai = cursor.getColumnIndex("tenLoai");
        int indexXuatXu = cursor.getColumnIndex("xuatXu");
        cursor.moveToFirst();
        int maLoai;
        String tenLoai,xuatXu;
        while (!cursor.isAfterLast()){
            maLoai = Integer.parseInt(cursor.getString(indexMaLoai));
            tenLoai = cursor.getString(indexTenLoai);
            xuatXu = cursor.getString(indexXuatXu);

            listLoaiXe.add(new LoaiXeModel(maLoai,tenLoai,xuatXu,false));
            cursor.moveToNext();
        }
        cursor.close();
        closeDataBase();
        return listLoaiXe;
    }

    public LoaiXeModel findLoaiXeByID(int idML){
        openDataBase();
        String query = "SELECT * FROM "+ TABLE_LOAIXE+" WHERE maLoai = " + idML;
        Cursor cursor = mSQLiteDatabase.rawQuery(query,null);
        if(cursor == null){
            return null;
        }
        cursor.moveToFirst();
        int maLoai = cursor.getInt(cursor.getColumnIndex("maLoai"));
        String tenLoai = cursor.getString(cursor.getColumnIndex("tenLoai"));
        String xuatXu = cursor.getString(cursor.getColumnIndex("xuatXu"));
        LoaiXeModel loaiXeModel = new LoaiXeModel(maLoai,tenLoai,xuatXu,false);
        closeDataBase();
        return loaiXeModel;
    }

    public List<DonHangModel> getListDonHang(){
        List<DonHangModel> listDonHangModel = new ArrayList<>();
        openDataBase();
        Cursor cursor = mSQLiteDatabase.rawQuery(GET_ALL_DON_HANG,null);
        if(cursor == null){
            return null;
        }
        int indexMADDH = cursor.getColumnIndex("maDDH");
        int indexMaXe = cursor.getColumnIndex("maXe");
        int indexSoLuong = cursor.getColumnIndex("soLuong");

        cursor.moveToFirst();
        int maDDH, maXe, soLuong;
        while (!cursor.isAfterLast()){
            maDDH = Integer.parseInt(cursor.getString(indexMADDH));
            maXe = Integer.parseInt(cursor.getString(indexMaXe));
            soLuong = Integer.parseInt(cursor.getString(indexSoLuong));
            listDonHangModel.add(new DonHangModel
                    (maDDH, maXe, soLuong, false));
            cursor.moveToNext();
        }
        cursor.close();
        closeDataBase();
        return listDonHangModel;
    }

    public List<DonDatHangModel> getListDonDatHang(){
        List<DonDatHangModel> listDonDatHangModel = new ArrayList<>();
        openDataBase();
        Cursor cursor = mSQLiteDatabase.rawQuery(GET_ALL_DON_DAT_HANG,null);
        if(cursor == null){
            return null;
        }
        int indexMADDH = cursor.getColumnIndex("maDDH");
        int indexNgayLap = cursor.getColumnIndex("ngayLap");

        cursor.moveToFirst();
        int maDDH;
        String ngayLap;
        while (!cursor.isAfterLast()){
            maDDH = Integer.parseInt(cursor.getString(indexMADDH));
            ngayLap = cursor.getString(indexNgayLap);
            listDonDatHangModel.add(new DonDatHangModel
                    (maDDH, ngayLap));
            cursor.moveToNext();
        }
        cursor.close();
        closeDataBase();
        return listDonDatHangModel;
    }

    public XeModel findXeByID(int idMX){
        openDataBase();
        String query = "SELECT * FROM "+ TABLE_QUANLYXE+" WHERE maXe = " + idMX;
        Cursor cursor = mSQLiteDatabase.rawQuery(query,null);
        if(cursor == null){
            return null;
        }
        cursor.moveToFirst();
        int maXe = cursor.getInt(cursor.getColumnIndex("maXe"));
        String tenXe = cursor.getString(cursor.getColumnIndex("tenXe"));
        String dungTich = cursor.getString(cursor.getColumnIndex("dungTich"));
        Double donGia = cursor.getDouble(cursor.getColumnIndex("donGia"));
        int maLoai = cursor.getInt(cursor.getColumnIndex("maLoai"));
        XeModel xeModel = new XeModel(maXe,tenXe,dungTich,donGia,maLoai,false);
        closeDataBase();
        return xeModel;
    }
    public List<XeModel> getListXeModelByMaLoai(int maLoaiParam) {
        List<XeModel> listXeModel = new ArrayList<>();
        openDataBase();
        String query = "SELECT * FROM "+ TABLE_QUANLYXE + " WHERE maLoai = " + maLoaiParam ;
        Cursor cursor = mSQLiteDatabase.rawQuery(query,null);
        if(cursor == null){
            return null;
        }
        int indexMaLoai = cursor.getColumnIndex("maLoai");
        int indexTenLoai = cursor.getColumnIndex("tenLoai");
        int indexXuatXu = cursor.getColumnIndex("xuatXu");
        cursor.moveToFirst();
        int maXe;
        String tenXe,dungTich;
        Double donGia;
        int maLoai;
        while (!cursor.isAfterLast()){
            maXe = cursor.getInt(cursor.getColumnIndex("maXe"));
            tenXe = cursor.getString(cursor.getColumnIndex("tenXe"));
            dungTich = cursor.getString(cursor.getColumnIndex("dungTich"));
            donGia = cursor.getDouble(cursor.getColumnIndex("donGia"));
            maLoai = cursor.getInt(cursor.getColumnIndex("maLoai"));
            listXeModel.add(new XeModel(maXe,tenXe,dungTich,donGia,maLoai,false));
            cursor.moveToNext();
        }
        cursor.close();
        closeDataBase();
        return listXeModel;
    }
    public DonDatHangModel findDonDatHangByID(int idMADDH){
        openDataBase();
        String query = "SELECT * FROM "+ TABLE_DONDATHANG+" WHERE maDDH = " + idMADDH;
        Cursor cursor = mSQLiteDatabase.rawQuery(query,null);
        if(cursor == null){
            return null;
        }
        cursor.moveToFirst();
        int maDDH = cursor.getInt(cursor.getColumnIndex("maDDH"));
        String ngayLap = cursor.getString(cursor.getColumnIndex("ngayLap"));
        DonDatHangModel donDatHangModel = new DonDatHangModel(maDDH,ngayLap);
        closeDataBase();
        return donDatHangModel;
    }

    public int getCount(String nameTable,String columnID){
        openDataBase();
        String query = "SELECT  * FROM "+ nameTable+" ORDER BY "+columnID+" DESC LIMIT 1";
        Cursor cursor = mSQLiteDatabase.rawQuery(query,null);
        if(cursor == null){
            return 0;
        }
        cursor.moveToFirst();
        int last = cursor.getInt(0);
        closeDataBase();
        return last;
    }
    public ResponseSQL insert(String tableName, String[] colums, String []dataColums){
        openDataBase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0;i<colums.length;++i){
            contentValues.put(colums[i],dataColums[i]);
        }
        long result = mSQLiteDatabase.insert(tableName,null,contentValues);
        ResponseSQL responseSQL = new ResponseSQL(result,result > -1);
        closeDataBase();
        return  responseSQL;
    }
    public boolean update(String tableName,String[] colums,String []dataColums,String where,String[] whereArgs){
        openDataBase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0;i<colums.length;++i){
            contentValues.put(colums[i],dataColums[i]);
        }
        long result = mSQLiteDatabase.update(tableName,contentValues,where,whereArgs);
        closeDataBase();
        return  result > 0;// -1 error, > -1 success
    }
    public ResponseSQL delete(String tableName,String where,String[] whereArgs){
        openDataBase();
        long result = mSQLiteDatabase.delete(tableName,where,whereArgs);
        ResponseSQL responseSQL = new ResponseSQL(result,result > 0);
        closeDataBase();
        return  responseSQL;
    }
}
