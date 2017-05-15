package com.example.tugberk.restoran.Model;

import com.example.tugberk.restoran.Tarih;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Tugberk on 6.05.2017.
 */

public class Abonelik {
    private int id;
    private Tarih baslangicTarihi;
    private Tarih bitisTarihi;
    private int frekans;
    private Tarih rezervasyonSaati;
    private Musteri musteri;
    private ArrayList<Rezervasyon> rezervasyonlar = new ArrayList<>();
    public static class DB{
        public static String TABLO_ADI = "ABONELIK";
        public static String ID = "ID";
        public static String MUSTERI_ID = "MUSTERI_ID";
        public static String BASLANGIC_TARIHI = "BASLANGIC_TARIHI";
        public static String BITIS_TARIHI = "BITIS_TARIHI";
        public static String FREKANS = "FREKANS";
        public static String REZERVASYON_SAATI = "REZERVASYON_SAATI";
    }

    public Abonelik(Tarih baslangicTarihi, Tarih bitisTarihi,
                    int frekans, Tarih rezervasyonSaati,Musteri musteri) {
        this.baslangicTarihi = baslangicTarihi;
        this.bitisTarihi = bitisTarihi;
        this.frekans = frekans;
        this.rezervasyonSaati = rezervasyonSaati;
        this.musteri = musteri;
    }

    public Abonelik(int id, Tarih baslangicTarihi, Tarih bitisTarihi, int frekans,
                    Tarih rezervasyonSaati, Musteri musteri) {
        this.id = id;
        this.baslangicTarihi = baslangicTarihi;
        this.bitisTarihi = bitisTarihi;
        this.frekans = frekans;
        this.rezervasyonSaati = rezervasyonSaati;
        this.musteri = musteri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public Tarih getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(Tarih baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public Tarih getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(Tarih bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }

    public int getFrekans() {
        return frekans;
    }

    public void setFrekans(int frekans) {
        this.frekans = frekans;
    }

    public Tarih getRezervasyonSaati() {
        return rezervasyonSaati;
    }

    public void setRezervasyonSaati(Tarih rezervasyonSaati) {
        this.rezervasyonSaati = rezervasyonSaati;
    }

    public ArrayList<Rezervasyon> getRezervasyonlar() {
        return rezervasyonlar;
    }

    public void setRezervasyonlar(ArrayList<Rezervasyon> rezervasyonlar) {
        this.rezervasyonlar = rezervasyonlar;
    }
}
