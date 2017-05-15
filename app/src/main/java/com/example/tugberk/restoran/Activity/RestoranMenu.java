package com.example.tugberk.restoran.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tugberk.restoran.R;

public class RestoranMenu extends AppCompatActivity {
    private Button rezervasyon;
    private Button yemek;
    private Button abonelik;
    private Button siparis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_menu);
        init();
        handleListeners();
    }
    private void init(){
        rezervasyon = (Button) findViewById(R.id.rezervasyonRestoran);
        yemek = (Button) findViewById(R.id.yemekIslem);
        abonelik = (Button) findViewById(R.id.abonelikIslem);
        siparis = (Button) findViewById(R.id.siparisIslem);
    }
    private void handleListeners(){
        rezervasyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RezervasyonRestoran.class);
                startActivity(i);
            }
        });
        yemek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RestoranYemek.class);
                startActivity(i);
            }
        });
        siparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RestoranSiparis.class);
                startActivity(i);
            }
        });
        abonelik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RestoranAbonelik.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RestoranMenu.this);
        builder.setCancelable(true);
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RestoranMenu.this.finish();
            }
        });
        builder.setMessage("Sistemden çıkış yapmak istediğinize emin misiniz?");
        builder.create().show();
    }
}
