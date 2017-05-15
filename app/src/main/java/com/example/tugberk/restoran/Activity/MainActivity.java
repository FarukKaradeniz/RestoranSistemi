package com.example.tugberk.restoran.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tugberk.restoran.R;

public class MainActivity extends AppCompatActivity {
    TextView resimYaziEkrani;
    ImageView resimEkraniResim;
    Button girisYapGonder;
    Button kayitOlGonder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleListeners();
    }
    private void init(){
        resimYaziEkrani = (TextView) findViewById(R.id.resimEkraniYazi);
        resimEkraniResim = (ImageView) findViewById(R.id.resimEkraniResim);
        girisYapGonder = (Button) findViewById(R.id.girisEkraninaGonder);
        kayitOlGonder = (Button) findViewById(R.id.yeniKayitaGonder);
    }
    private void handleListeners(){
        girisYapGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        kayitOlGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), KayitolActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                MainActivity.this.finish();
            }
        });
        builder.setMessage("Uygulamadan çıkmak istediğinize emin misiniz?");
        builder.create().show();
    }
}