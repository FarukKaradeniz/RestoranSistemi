package com.example.tugberk.restoran.VeriIletisimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.tugberk.restoran.Model.Kontenjan;
import com.example.tugberk.restoran.Model.Rezervasyon;

/**
 * Created by Tugberk on 13.05.2017.
 */

public class KontenjanVeriIletisimi extends VeriIletisimi {
    public KontenjanVeriIletisimi(Context ctx) {
        super(ctx);
    }
    public int kisiSayisi(String tarih, String saat){

        String sql = "select " + Kontenjan.DB.KISI_SAYISI + " from " +
                Kontenjan.DB.TABLO_ADI + " where " +  Kontenjan.DB.TARIH  + " = ? and " +
                Kontenjan.DB.SAAT + " = ? ";
        Cursor c = db.rawQuery(sql,new String[]{tarih,saat});
        int i;
        if(c.moveToFirst()){
            i = c.getInt(0);
        }else{
            i = 0;
        }
        return i;
    }
    public void kisiSayisiniGuncelle(Rezervasyon r,int eskiKisiSayisi){

        if(eskiKisiSayisi == 0){
            ContentValues cv = new ContentValues();
            cv.put(Kontenjan.DB.TARIH,r.getTarih().tarihToString());
            cv.put(Kontenjan.DB.SAAT,r.getTarih().saatToString());
            cv.put(Kontenjan.DB.KISI_SAYISI,r.getKisiSayisi());
            db.insert(Kontenjan.DB.TABLO_ADI,null,cv);
        }else{
            String whereClause = Kontenjan.DB.TARIH + " = ? and " +
                    Kontenjan.DB.SAAT + " = ?";
            ContentValues cv = new ContentValues();
            cv.put(Kontenjan.DB.KISI_SAYISI,kisiSayisi(r.getTarih().tarihToString(),
                    r.getTarih().saatToString()) + r.getKisiSayisi());
            db.update(Kontenjan.DB.TABLO_ADI,cv,whereClause,new String[]{
                    r.getTarih().tarihToString(),r.getTarih().saatToString()});
        }

    }
}
