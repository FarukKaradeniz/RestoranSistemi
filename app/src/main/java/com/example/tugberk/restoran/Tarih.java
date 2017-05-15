package com.example.tugberk.restoran;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Tugberk on 7.05.2017.
 */

public class Tarih extends GregorianCalendar{
    public String tarihToString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.getTime());
    }
    public String saatToString(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
        return sdf.format(this.getTime());
    }
    public static Tarih toTarih(String tarih, String saat){
        String temp = tarih + " " + saat;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH.mm");
        Tarih newTarih = new Tarih();
        try {
            newTarih.setTime(sdf.parse(temp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTarih;
    }
}
