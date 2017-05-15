package com.example.tugberk.restoran.Activity;

import android.content.Intent;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.MusteriVeriIletisimi;

public class KayitolActivity extends AppCompatActivity {
    private EditText isimEdt;
    private EditText soyisimEdt;
    private EditText emailEdt;
    private EditText sifreEdt;
    private EditText telefonEdt;
    private EditText adresEdt;
    private Button kayitOl;
    private String isim, soyisim, email, sifre, telefon, adres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitol);
        init();
        kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isim = isimEdt.getText().toString();
                soyisim = soyisimEdt.getText().toString();
                email = emailEdt.getText().toString();
                sifre = sifreEdt.getText().toString();
                adres = adresEdt.getText().toString();
                telefon = telefonEdt.getText().toString();

                if(!isInfoEmpty(new String[]{isim, soyisim, email, sifre, adres, telefon})) {
                    Musteri m = new Musteri(isim,soyisim,email, sifre, adres, telefon);
                    MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(KayitolActivity.this);
                    boolean b = mvi.uyeOl(m);
                    if(b){   //kayit olma basarili olduysa kayit formundaki alanların bos hale getirilme islemi
                        Toast.makeText(getApplicationContext(), "Kayıt işleminiz gerçekleştirilmiştir", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(KayitolActivity.this,MusteriMenu.class);
                        intent.putExtra("MusteriID",m.getId());
                        startActivity(intent);
                        KayitolActivity.this.finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"E-Posta veya telefon zaten mevcut.",Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Lütfen Alanların Hepsini Doldurunuz", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init(){
        isimEdt = (EditText) findViewById(R.id.isimEdt);
        soyisimEdt = (EditText) findViewById(R.id.soyisimEdt);
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        sifreEdt = (EditText) findViewById(R.id.sifreEdt);
        telefonEdt = (EditText) findViewById(R.id.telefonEdt);
        adresEdt = (EditText) findViewById(R.id.adresEdt);
        kayitOl = (Button) findViewById(R.id.kayitOl);
    }
    private boolean isInfoEmpty(String [] info){
        for(String s : info){
            if(s.isEmpty())
                return true;
        }
        return false;
    }
}
