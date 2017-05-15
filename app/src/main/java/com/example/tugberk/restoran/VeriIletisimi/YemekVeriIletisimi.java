package com.example.tugberk.restoran.VeriIletisimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.tugberk.restoran.Model.Yemek;

import java.util.ArrayList;

/**
 * Created by Tugberk on 13.05.2017.
 */

public class YemekVeriIletisimi extends VeriIletisimi {
    public YemekVeriIletisimi(Context ctx) {
        super(ctx);
    }
    public boolean yemekKaydet(Yemek y){
        if(getYemekId(y) != -1){
            return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(Yemek.DB.ISIM,y.getIsim());
        cv.put(Yemek.DB.FIYAT,y.getFiyat());
        db.insert(Yemek.DB.TABLO_ADI,null,cv);
        y.setId(getYemekId(y));

        return true;
    }
    public int getYemekId(Yemek y){

        String sql = "select " + Yemek.DB.ID + " from " + Yemek.DB.TABLO_ADI +
                " where " + Yemek.DB.ISIM + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{y.getIsim()});
        int i;
        if(c.moveToFirst()){
            i = c.getInt(0);
        }else{
            i = -1;
        }
        return i;
    }
    public void fiyatGuncelle(Yemek y, double fiyat){

        ContentValues cv = new ContentValues();
        cv.put(Yemek.DB.FIYAT, fiyat);
        String whereClause = Yemek.DB.ID + " = ? ";
        db.update(Yemek.DB.TABLO_ADI, cv, whereClause,
                new String[]{String.valueOf(y.getId())});

    }
    public void yemekSilme(Yemek y){

        String whereClause = Yemek.DB.ID + " = ? ";
        db.delete(Yemek.DB.TABLO_ADI,whereClause,new String[]{String.valueOf(y.getId())});

    }

    public ArrayList<Yemek> yemekListesi(){

        ArrayList<Yemek> yemekler = new ArrayList<>();
        String sql = "select * from " + Yemek.DB.TABLO_ADI;
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            Yemek yemek = new Yemek(c.getString(1), c.getDouble(2));
            yemek.setId(c.getInt(0));
            yemekler.add(yemek);
            c.moveToNext();
        }
        return yemekler;
    }
    public Yemek isimdenYemekDondur(String isim){

        String sql = "select * from " + Yemek.DB.TABLO_ADI + " where " +
                Yemek.DB.ISIM + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{isim});
        Yemek y;
        if(c.moveToFirst()){
            y = new Yemek(c.getString(1),c.getInt(0),c.getDouble(2));
        }else{
            y = null;
        }
        return y;
    }
    public Yemek idDenYemekDondur(int id){

        String sql = "select * from " + Yemek.DB.TABLO_ADI +
                " where " + Yemek.DB.ID + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(id)});
        Yemek y;
        if(c.moveToFirst()){
            y = new Yemek(c.getString(1),c.getInt(0),c.getDouble(2));
        }else{
            y = null;
        }
        return y;
    }
}
