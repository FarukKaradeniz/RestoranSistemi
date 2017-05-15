package com.example.tugberk.restoran.VeriIletisimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Model.Rezervasyon;
import com.example.tugberk.restoran.RestoranSistemi;
import com.example.tugberk.restoran.Tarih;

import java.util.ArrayList;

/**
 * Created by Tugberk on 13.05.2017.
 */

public class MusteriVeriIletisimi extends VeriIletisimi{
    public MusteriVeriIletisimi(Context ctx) {
        super(ctx);
    }

    public boolean uyeOl(Musteri m){
        if(getMusteriId(m) != -1){
            return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(Musteri.DB.AD,m.getAd());
        cv.put(Musteri.DB.SOYAD,m.getSoyAd());
        cv.put(Musteri.DB.EMAIL,m.geteMail());
        cv.put(Musteri.DB.SIFRE,m.getSifre());
        cv.put(Musteri.DB.ADRES,m.getAdres());
        cv.put(Musteri.DB.TELNO,m.getTelefon());
        db.insert(Musteri.DB.TABLO_ADI,null,cv);
        m.setId(getMusteriId(m));

        return true;
    }
    private int getMusteriId(Musteri m){

        String sql = "select " + Musteri.DB.ID + " from "
         + Musteri.DB.TABLO_ADI + " where " + Musteri.DB.EMAIL + " = ? or "
                 + Musteri.DB.TELNO + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{m.geteMail(),m.getTelefon()});
        int i;
        if(c.moveToFirst()){
            i = c.getInt(0);
        }else{
            i = -1;
        }

        return i;
    }
    public Musteri logIn(String eMail, String sifre){

        String sql = "select * from " + Musteri.DB.TABLO_ADI +
                " where " + Musteri.DB.EMAIL + " = ? and " + Musteri.DB.SIFRE + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{eMail,sifre});
        Musteri m;
        if(c.moveToFirst()){
            m = new Musteri(c.getString(1),c.getString(2),c.getString(3),c.getString(4)
            ,c.getString(5),c.getString(6),c.getInt(0));
        }else{
            m = null;
        }

        return m;
    }
    public ArrayList<Rezervasyon> rezervasyonlarıGoster(Musteri m){

        String sql = "select " + Rezervasyon.DB.TARIH + " , " + Rezervasyon.DB.SAAT
                + " , " + Rezervasyon.DB.KISI_SAYISI + " from " + Rezervasyon.DB.TABLO_ADI +
                " where " + Rezervasyon.DB.MUSTERI_ID + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(m.getId())});
        ArrayList<Rezervasyon> r = new ArrayList<>();
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(Tarih.toTarih(c.getString(0), c.getString(1)).after(Tarih.getInstance())) {
                r.add(new Rezervasyon(c.getInt(2), Tarih.toTarih(
                        c.getString(0), c.getString(1)), m));
            }
            c.moveToNext();
        }

        return r;
    }
    public Musteri idDenMusteriOlustur(int id){

        String sql = "select * from " + Musteri.DB.TABLO_ADI +
                " where " + Musteri.DB.ID + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(id)});
        Musteri m;
        if(c.moveToFirst()){
            m =  new Musteri(c.getString(1),c.getString(2),c.getString(3),
                    c.getString(4),c.getString(5),c.getString(6),c.getInt(0));
        }else{
            m = null;
        }

        return m;
    }
}
