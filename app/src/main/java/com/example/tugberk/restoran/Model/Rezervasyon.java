package com.example.tugberk.restoran.Model;

import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Tarih;

/**
 * Created by Tugberk on 6.05.2017.
 */

public class Rezervasyon {
    public static final int KAPASITE = 50;
    private int id;
    private Tarih tarih;
    private int kisiSayisi;
    private Musteri musteri;
    public static class DB{
        public static String TABLO_ADI = "REZERVASYON";
        public static String ID = "ID";
        public static String TARIH = "TARIH";
        public static String SAAT = "SAAT";
        public static String MUSTERI_ID = "MUSTERI_ID";
        public static String KISI_SAYISI = "KISI_SAYISI";
    }

    public Rezervasyon(int kisiSayisi, Tarih tarih, Musteri musteri) {
        this.kisiSayisi = kisiSayisi;
        this.tarih = tarih;
        this.musteri = musteri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tarih getTarih() {
        return tarih;
    }

    public void setTarih(Tarih tarih) {
        this.tarih = tarih;
    }

    public int getKisiSayisi() {
        return kisiSayisi;
    }

    public void setKisiSayisi(int kisiSayisi) {
        this.kisiSayisi = kisiSayisi;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public String rezervasyonToString(){
        return tarih.tarihToString() + " " + tarih.saatToString() + " Kişi Sayısı : " + kisiSayisi;
    }
}
