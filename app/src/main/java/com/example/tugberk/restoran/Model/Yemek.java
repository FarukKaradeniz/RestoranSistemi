package com.example.tugberk.restoran.Model;

/**
 * Created by Tugberk on 6.05.2017.
 */

public class Yemek {
    private String isim;
    private int id;
    private double fiyat;
    public static class DB{
        public static String TABLO_ADI = "YEMEK";
        public static String ID = "ID";
        public static String ISIM = "ISIM";
        public static String FIYAT = "FIYAT";
    }

    public Yemek(String isim, double fiyat) {
        this.isim = isim;
        this.fiyat = fiyat;
    }

    public Yemek(String isim, int id, double fiyat) {
        this.isim = isim;
        this.id = id;
        this.fiyat = fiyat;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }
}
