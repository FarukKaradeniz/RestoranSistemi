package com.example.tugberk.restoran.VeriIletisimi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.tugberk.restoran.RestoranSistemi;

/**
 * Created by Tugberk on 13.05.2017.
 */

public class VeriIletisimi {
    protected SQLiteDatabase db;
    protected RestoranSistemi restoranSistemi;
    protected Context ctx;
    public VeriIletisimi(Context ctx){
        restoranSistemi = new RestoranSistemi(ctx.getApplicationContext());
        ac();
        this.ctx = ctx.getApplicationContext();
    }
    public void ac(){
        db = restoranSistemi.getWritableDatabase();
    }
    public void kapat(){
        restoranSistemi.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
