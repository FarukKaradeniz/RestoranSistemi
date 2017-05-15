package com.example.tugberk.restoran.VeriIletisimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.tugberk.restoran.Model.Kontenjan;
import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Model.Rezervasyon;
import com.example.tugberk.restoran.Tarih;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Tugberk on 13.05.2017.
 */

public class RezervasyonVeriIletisimi extends VeriIletisimi {
    public RezervasyonVeriIletisimi(Context ctx) {
        super(ctx);
    }
    public boolean rezervasyonYap(Rezervasyon r){
        if(rezervasyonlarCakisiyorMu(r)){
            return false;
        }
        KontenjanVeriIletisimi kvi = new KontenjanVeriIletisimi(ctx);
        if(r.getKisiSayisi() + kvi.kisiSayisi(r.getTarih().tarihToString(),
                r.getTarih().saatToString()) > Kontenjan.KAPASITE){
            return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(Rezervasyon.DB.MUSTERI_ID,r.getMusteri().getId());
        cv.put(Rezervasyon.DB.TARIH,r.getTarih().tarihToString());
        cv.put(Rezervasyon.DB.SAAT,r.getTarih().saatToString());
        cv.put(Rezervasyon.DB.KISI_SAYISI,r.getKisiSayisi());
        db.insert(Rezervasyon.DB.TABLO_ADI,null,cv);
        kvi.kisiSayisiniGuncelle(r,kvi.kisiSayisi(r.getTarih().tarihToString(),
                r.getTarih().saatToString()));
        r.setId(getRezervasyonId(r));

        return true;
    }
    public boolean rezervasyonlarCakisiyorMu(Rezervasyon r){

        String sql = "select " + Rezervasyon.DB.ID + " from " +
                Rezervasyon.DB.TABLO_ADI + " where " +
                Rezervasyon.DB.MUSTERI_ID + " = ? and " +
                Rezervasyon.DB.TARIH + " = ? and " +
                Rezervasyon.DB.SAAT + " = ? ";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(r.getMusteri().getId()),
        r.getTarih().tarihToString(),r.getTarih().saatToString()});

        return c.moveToFirst();
    }
    public int getRezervasyonId(Rezervasyon r){

        String sql = "select " + Rezervasyon.DB.ID + " from " +
                Rezervasyon.DB.TABLO_ADI + " where " +
                Rezervasyon.DB.TARIH + " = ? and " + Rezervasyon.DB.SAAT  +
                " = ?";
        Cursor c = db.rawQuery(sql,new String[]{r.getTarih().tarihToString(),
        r.getTarih().saatToString()});
        c.moveToFirst();

        return c.getInt(0);
    }
    public void rezervasyonSilme(Rezervasyon r){

        String whereClause = Rezervasyon.DB.TARIH + " = ? and " +
                Rezervasyon.DB.SAAT + " = ? and " +
                Rezervasyon.DB.MUSTERI_ID + " = ?";
        db.delete(Rezervasyon.DB.TABLO_ADI,whereClause,
                new String[]{r.getTarih().tarihToString(),r.getTarih().saatToString(),
                String.valueOf(r.getMusteri().getId())});

    }
    public ArrayList<Rezervasyon> zamanaGoreRezervasyonlar(String tarih, String saat){

        MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(ctx);
        String sql = "select " + Rezervasyon.DB.MUSTERI_ID + " , " +
                Rezervasyon.DB.KISI_SAYISI + " from " + Rezervasyon.DB.TABLO_ADI +
        " where " + Rezervasyon.DB.TARIH + " = ? and " +
                Rezervasyon.DB.SAAT + " = ? ";
        Cursor c = db.rawQuery(sql,new String[]{tarih,saat});
        c.moveToFirst();
        ArrayList<Rezervasyon> r = new ArrayList<>();
        while(!c.isAfterLast()){
            r.add(new Rezervasyon(c.getInt(1), Tarih.toTarih(tarih,saat),
                    mvi.idDenMusteriOlustur(c.getInt(0))));
            c.moveToNext();
        }
        return r;
    }
}
