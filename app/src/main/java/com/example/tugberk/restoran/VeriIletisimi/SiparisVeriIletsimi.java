package com.example.tugberk.restoran.VeriIletisimi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Model.Siparis;
import com.example.tugberk.restoran.Model.SiparisYemekleri;
import com.example.tugberk.restoran.Model.Yemek;

import java.util.ArrayList;

/**
 * Created by Tugberk on 14.05.2017.
 */

public class SiparisVeriIletsimi  extends VeriIletisimi {
    public SiparisVeriIletsimi(Context ctx) {
        super(ctx);
    }
    public void siparisEkle(Siparis s){

        ContentValues cv = new ContentValues();
        cv.put(Siparis.DB.MUSTERI_ID, s.getMusteri().getId());
        db.insert(Siparis.DB.TABLO_ADI,null,cv);
        s.setId(sonSiparisIdSiniGeriDondur(s));
        SiparisYemekVeriIletisimi syvi = new SiparisYemekVeriIletisimi(ctx);
        syvi.siparisYemekleriniEkle(s);

    }
    public int sonSiparisIdSiniGeriDondur(Siparis s){

        String sql = "select " + Siparis.DB.ID + " from " + Siparis.DB.TABLO_ADI +
                " where " + Siparis.DB.MUSTERI_ID + " = ?";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(s.getMusteri().getId())});
        int i;
        if(c.moveToLast()){
            i = c.getInt(0);
        }else{
            i = -1;
        }

        return i;
    }
    public void siparisSil(Siparis s){

        String whereClause = Siparis.DB.ID + " = ?";
        db.delete(Siparis.DB.TABLO_ADI,whereClause,new String[]{String.valueOf(s.getId())});
        SiparisYemekVeriIletisimi syvi = new SiparisYemekVeriIletisimi(ctx);
        syvi.siparisYemekleriniSil(s);

    }
    public ArrayList<Siparis> siparisListesiniDondur(){

        String sql = "select * from " + Siparis.DB.TABLO_ADI;
        Cursor siparisCursor = db.rawQuery(sql,null);
        //kapat();
        siparisCursor.moveToFirst();
        ArrayList<Siparis> siparisler = new ArrayList<>();
        while(!siparisCursor.isAfterLast()){
            MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(ctx);
            Musteri musteri = mvi.idDenMusteriOlustur(siparisCursor.getInt(1));
            SiparisYemekVeriIletisimi syvi = new SiparisYemekVeriIletisimi(ctx);
            ArrayList<Integer> yemekIdleri = syvi.siparisYemekleriIdleriniDOndur(siparisCursor.getInt(0));
            ArrayList<Yemek> yemekler = new ArrayList<>();
            YemekVeriIletisimi yvi = new YemekVeriIletisimi(ctx);
            for(Integer i : yemekIdleri){
                yemekler.add(yvi.idDenYemekDondur(i));
            }
            //yvi.kapat();
            siparisler.add(new Siparis(musteri,yemekler));
            siparisCursor.moveToNext();
        }
        return siparisler;
    }
}
