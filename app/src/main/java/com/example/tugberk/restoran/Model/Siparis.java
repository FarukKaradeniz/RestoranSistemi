package com.example.tugberk.restoran.Model;

import java.util.ArrayList;

/**
 * Created by Tugberk on 6.05.2017.
 */

public class Siparis {
    private Musteri musteri;
    private ArrayList<Yemek> yemekler;
    private int id;
    public static class DB{
        public static String TABLO_ADI = "SIPARIS";
        public static String ID = "ID";
        public static String MUSTERI_ID = "MUSTERI_ID";
        public static String YEMEK_ID = "YEMEK_ID";
    }

    public Siparis(Musteri musteri, ArrayList<Yemek> yemekler) {
        this.musteri = musteri;
        this.yemekler = yemekler;
    }

    public Siparis(Musteri musteri, ArrayList<Yemek> yemekler, int id) {
        this.musteri = musteri;
        this.yemekler = yemekler;
        this.id = id;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public ArrayList<Yemek> getYemekler() {
        return yemekler;
    }

    public void setYemekler(ArrayList<Yemek> yemekler) {
        this.yemekler = yemekler;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
