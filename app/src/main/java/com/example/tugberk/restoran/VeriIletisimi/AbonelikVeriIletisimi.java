package com.example.tugberk.restoran.VeriIletisimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.tugberk.restoran.Model.Abonelik;
import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Model.Rezervasyon;
import com.example.tugberk.restoran.Tarih;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Tugberk on 14.05.2017.
 */

public class AbonelikVeriIletisimi extends VeriIletisimi {
    public AbonelikVeriIletisimi(Context ctx) {
        super(ctx);
    }
    public void abonelikOlustur(Abonelik abonelik){

        ContentValues cv = new ContentValues();
        cv.put(Abonelik.DB.BASLANGIC_TARIHI,abonelik.getBaslangicTarihi().tarihToString());
        cv.put(Abonelik.DB.BITIS_TARIHI,abonelik.getBitisTarihi().tarihToString());
        cv.put(Abonelik.DB.REZERVASYON_SAATI,abonelik.getRezervasyonSaati().saatToString());
        cv.put(Abonelik.DB.FREKANS,abonelik.getFrekans());
        cv.put(Abonelik.DB.MUSTERI_ID,abonelik.getMusteri().getId());
        db.insert(Abonelik.DB.TABLO_ADI,null,cv);
        abonelik.setId(abonelikIdSiniDondur(abonelik));
        abonelikRezervasyonlariniOlustur(abonelik);

    }
    public int abonelikIdSiniDondur(Abonelik abonelik){

        String sql = "select " + Abonelik.DB.ID + " from " +
                Abonelik.DB.TABLO_ADI + " where " + Abonelik.DB.MUSTERI_ID +
                " = ? ";
        Cursor c = db.rawQuery(sql,new String[]{
                String.valueOf(abonelik.getMusteri().getId())});
        int i;
        if(c.moveToFirst()){
            i = c.getInt(0);
        }else{
            i = -1;
        }
        return i;
    }
    public void abonelikRezervasyonlariniOlustur(Abonelik abonelik){

        RezervasyonVeriIletisimi rvi = new RezervasyonVeriIletisimi(ctx);
        Tarih rezervasyonTarihi = abonelik.getBaslangicTarihi();
        while(rezervasyonTarihi.before(abonelik.getBitisTarihi())){
            Rezervasyon r = new Rezervasyon(1,rezervasyonTarihi,abonelik.getMusteri());
            rvi.rezervasyonYap(r);
            abonelik.getRezervasyonlar().add(r);
            rezervasyonTarihi.add(Calendar.DATE,abonelik.getFrekans());
        }

    }
    public void abonelikIptali(Abonelik abonelik){

        String whereClause = Abonelik.DB.ID + " = ?";
        db.delete(Abonelik.DB.TABLO_ADI,whereClause,new String[]{String.valueOf(abonelik.getId())});

    }

    public Abonelik musteriIdSindenAbonelikDondur(Musteri m){

        String sql = "select * from " +  Abonelik.DB.TABLO_ADI + " where " +
                Abonelik.DB.MUSTERI_ID + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(m.getId())});
        Abonelik a;
        if(c.moveToFirst()){
            Tarih baslangicTarihi = Tarih.toTarih(c.getString(1),c.getString(2));
            Tarih bitisTarihi = Tarih.toTarih(c.getString(4),c.getString(2));
            a = new Abonelik(c.getInt(0),baslangicTarihi,bitisTarihi,
                    c.getInt(3),baslangicTarihi,m);
        }
        else{
            a = null;
        }
        return a;
    }
    public ArrayList<Abonelik> aktifAbonelikleriDondur(){

        ArrayList<Abonelik> abonelikler = new ArrayList<>();
        String sql = "select * from " + Abonelik.DB.TABLO_ADI;
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(Tarih.toTarih(c.getString(4),c.getString(2)).after(Tarih.getInstance())){
                MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(ctx);
                Musteri m = mvi.idDenMusteriOlustur(c.getInt(5));
                Tarih baslangic = Tarih.toTarih(c.getString(0),c.getString(2));
                Tarih bitis = Tarih.toTarih(c.getString(4),c.getString(2));
                Abonelik abonelik = new Abonelik(c.getInt(0),baslangic,bitis,c.getInt(3),
                        baslangic,m);
                abonelikler.add(abonelik);

            }
            c.moveToNext();
        }
        return abonelikler;
    }
}
