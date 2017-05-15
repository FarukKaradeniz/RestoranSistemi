package com.example.tugberk.restoran;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tugberk.restoran.Model.Abonelik;
import com.example.tugberk.restoran.Model.Kontenjan;
import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Model.Rezervasyon;
import com.example.tugberk.restoran.Model.Siparis;
import com.example.tugberk.restoran.Model.SiparisYemekleri;
import com.example.tugberk.restoran.Model.Yemek;

/**
 * Created by Tugberk on 7.05.2017.
 */

public class RestoranSistemi extends SQLiteOpenHelper {

    public RestoranSistemi(Context c){
        super(c,"Restoran Sistemi",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String musteri = "create table " + Musteri.DB.TABLO_ADI + " ( " +
                Musteri.DB.ID +" integer primary key autoincrement, " +
                Musteri.DB.AD + " text, " +
                Musteri.DB.SOYAD + " text, " +
                Musteri.DB.EMAIL + " text unique, " +
                Musteri.DB.SIFRE + " text, " +
                Musteri.DB.ADRES + " text, " +
                Musteri.DB.TELNO + " text unique)";
        db.execSQL(musteri);
        String mevcut = "create table " + Kontenjan.DB.TABLO_ADI + " (" +
                Kontenjan.DB.TARIH + " text, " +
                Kontenjan.DB.SAAT + " text, " +
                Kontenjan.DB.KISI_SAYISI + " integer)";
        db.execSQL(mevcut);
        String yemek = "create table " + Yemek.DB.TABLO_ADI + " (" +
                Yemek.DB.ID + " integer primary key autoincrement, " +
                Yemek.DB.ISIM + " text unique, " +
                Yemek.DB.FIYAT + " real)";
        db.execSQL(yemek);
        String rezervasyon = "create table " + Rezervasyon.DB.TABLO_ADI + " (" +
                Rezervasyon.DB.ID + " integer primary key autoincrement, " +
                Rezervasyon.DB.MUSTERI_ID + " int, " +
                Rezervasyon.DB.TARIH + " text, " +
                Rezervasyon.DB.SAAT + " text, " +
                Rezervasyon.DB.KISI_SAYISI + " integer, " +
                "foreign key (" + Rezervasyon.DB.MUSTERI_ID + ")" +
                " references " + Musteri.DB.TABLO_ADI + "(" + Musteri.DB.ID + "))";
        db.execSQL(rezervasyon);
        String siparis = "create table " + Siparis.DB.TABLO_ADI + " (" +
                Siparis.DB.ID + " integer primary key autoincrement, " +
                Siparis.DB.MUSTERI_ID + " integer, " +
                "foreign key (" + Siparis.DB.MUSTERI_ID + ")" +
                " references " + Musteri.DB.TABLO_ADI + "(" + Musteri.DB.ID + "))";
        db.execSQL(siparis);
        String siparisYemek = "create table " + SiparisYemekleri.DB.TABLO_ADI + " (" +
                SiparisYemekleri.DB.SIPARIS_ID + " integer, " +
                SiparisYemekleri.DB.YEMEK_ID + " integer, " +
                "foreign key (" + SiparisYemekleri.DB.SIPARIS_ID + ")" +
                " references " + Siparis.DB.TABLO_ADI + "(" + Siparis.DB.ID + "), " +
                " foreign key (" + SiparisYemekleri.DB.YEMEK_ID + ")" +
                " references " + Yemek.DB.TABLO_ADI + "(" + Yemek.DB.ID + "))";
        db.execSQL(siparisYemek);
        String abonelik = "create table " + Abonelik.DB.TABLO_ADI + " ( " +
                Abonelik.DB.ID + " integer primary key autoincrement, " +
                Abonelik.DB.BASLANGIC_TARIHI + " text, " +
                Abonelik.DB.REZERVASYON_SAATI + " text, " +
                Abonelik.DB.FREKANS + " integer, " +
                Abonelik.DB.BITIS_TARIHI + " text, " +
                Abonelik.DB.MUSTERI_ID + " integer, " +
                "foreign key (" + Abonelik.DB.MUSTERI_ID + ") references " +
                        Musteri.DB.TABLO_ADI + "(" + Musteri.DB.ID + "))" ;
        db.execSQL(abonelik);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
