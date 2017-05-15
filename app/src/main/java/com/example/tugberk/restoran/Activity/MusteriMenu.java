package com.example.tugberk.restoran.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tugberk.restoran.Model.Abonelik;
import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.AbonelikVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.MusteriVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.VeriIletisimi;

public class MusteriMenu extends AppCompatActivity {
    private Button toRezervasyon;
    private Button toSiparis;
    private Button toAbonelik;
    private TextView musteriBilgi;
    private Musteri musteri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_menu);
        setTitle("Menü");
        init();
        Intent intent = getIntent();
        MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(MusteriMenu.this);
        musteri = mvi.idDenMusteriOlustur(intent.getIntExtra("MusteriID",0));
        mvi.kapat();
        musteriBilgi.setText("Hoş Geldiniz Sayın, \n" + musteri.getAd() + " " + musteri.getSoyAd());
        toRezervasyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RezervasyonMusteriActivity.class);
                i.putExtra("MusteriID",musteri.getId());
                startActivity(i);
            }
        });
        toSiparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SiparisMusteriActivity.class);
                i.putExtra("MusteriID",musteri.getId());
                startActivity(i);            }
        });
        toAbonelik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbonelikVeriIletisimi avi = new AbonelikVeriIletisimi(MusteriMenu.this);
                Abonelik a = avi.musteriIdSindenAbonelikDondur(musteri);
                musteri.setAbonelik(a);
                if(a == null){
                    Intent i = new Intent(getApplicationContext(), AbonelikYokMusteriActivity.class);
                    i.putExtra("MusteriID",musteri.getId());
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), AbonelikVarMusteriActivity.class);
                    i.putExtra("MusteriID",musteri.getId());
                    i.putExtra("AbonelikID",a.getId());
                    startActivity(i);
                }
            }
        });
    }
    private void init(){
        toRezervasyon = (Button) findViewById(R.id.toRez);
        toSiparis = (Button) findViewById(R.id.toSiparis);
        toAbonelik = (Button) findViewById(R.id.toAbonelik);
        musteriBilgi = (TextView) findViewById(R.id.musteriHosgeldi);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MusteriMenu.this);
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

                MusteriMenu.this.finish();
            }
        });
        builder.setMessage("Sistemden çıkış yapmak istediğinize emin misiniz?");
        builder.create().show();
    }
}
