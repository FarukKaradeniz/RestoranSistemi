package com.example.tugberk.restoran.VeriIletisimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.tugberk.restoran.Model.Siparis;
import com.example.tugberk.restoran.Model.SiparisYemekleri;
import com.example.tugberk.restoran.Model.Yemek;

import java.util.ArrayList;

/**
 * Created by Tugberk on 14.05.2017.
 */

public class SiparisYemekVeriIletisimi extends VeriIletisimi {
    public SiparisYemekVeriIletisimi(Context ctx) {
        super(ctx);
    }
    public void siparisYemekleriniEkle(Siparis s){

        ContentValues cv = new ContentValues();
        cv.put(SiparisYemekleri.DB.SIPARIS_ID,s.getId());
        for(Yemek y : s.getYemekler()){
            int i = y.getId();
            cv.put(SiparisYemekleri.DB.YEMEK_ID,y.getId());
            db.insert(SiparisYemekleri.DB.TABLO_ADI,null,cv);
        }

    }
    public  void siparisYemekleriniSil(Siparis s){

        String whereClause = SiparisYemekleri.DB.SIPARIS_ID + " = ?";
        db.delete(SiparisYemekleri.DB.TABLO_ADI,whereClause,new String[]{String.valueOf(s.getId())});

    }
    public ArrayList<Integer> siparisYemekleriIdleriniDOndur(int siparisId){

        String sql = "select " + SiparisYemekleri.DB.YEMEK_ID + " from " +
                SiparisYemekleri.DB.TABLO_ADI + " where " + SiparisYemekleri.DB.SIPARIS_ID + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(siparisId)});
        ArrayList<Integer> yemekIdLeri = new ArrayList<>();
        c.moveToFirst();
        while(!c.isAfterLast()){
            int i = c.getInt(0);
            yemekIdLeri.add(i);
            c.moveToNext();
        }
        return yemekIdLeri;
    }
}
