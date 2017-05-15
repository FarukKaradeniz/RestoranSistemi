package com.example.tugberk.restoran.Model;

import java.util.ArrayList;

/**
 * Created by Tugberk on 6.05.2017.
 */

public class Musteri {
    private String ad;
    private String soyAd;
    private String eMail;
    private String sifre;
    private String adres;
    private String telefon;
    private Abonelik abonelik;
    public static class RestoranLogIn{
        public static String EMAIL = "restoran@gmail.com";
        public static String SIFRE = "123";
    }
    private int id;
    private ArrayList<Rezervasyon> rezervasyonlar;
    public static class DB{
        public static String TABLO_ADI = "MUSTERI";
        public static String ID = "MUSTERI_ID";
        public static String AD = "AD";
        public static String SOYAD = "SOYAD";
        public static String EMAIL = "EMAIL";
        public static String SIFRE = "SIFRE";
        public static String ADRES = "ADRES";
        public static String TELNO = "TELNO";
    }

    public Musteri(String ad, String soyAd, String eMail,
                   String sifre, String adres, String telefon) {
        this.ad = ad;
        this.soyAd = soyAd;
        this.eMail = eMail;
        this.sifre = sifre;
        this.adres = adres;
        this.telefon = telefon;
        //rezervasyonlariEkle();
    }

    public Musteri(String ad, String soyAd,
                   String eMail, String sifre, String adres, String telefon, int id) {
        this.ad = ad;
        this.soyAd = soyAd;
        this.eMail = eMail;
        this.sifre = sifre;
        this.adres = adres;
        this.telefon = telefon;
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyAd() {
        return soyAd;
    }

    public void setSoyAd(String soyAd) {
        this.soyAd = soyAd;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Rezervasyon> getRezervasyonlar() {
        return rezervasyonlar;
    }

    public void setRezervasyonlar(ArrayList<Rezervasyon> rezervasyonlar) {
        this.rezervasyonlar = rezervasyonlar;
    }

    public Abonelik getAbonelik() {
        return abonelik;
    }

    public void setAbonelik(Abonelik abonelik) {
        this.abonelik = abonelik;
    }
}

