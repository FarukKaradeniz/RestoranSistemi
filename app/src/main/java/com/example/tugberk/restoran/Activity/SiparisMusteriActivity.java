package com.example.tugberk.restoran.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Model.Siparis;
import com.example.tugberk.restoran.Model.Yemek;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.MusteriVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.SiparisVeriIletsimi;
import com.example.tugberk.restoran.VeriIletisimi.SiparisYemekVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.VeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.YemekVeriIletisimi;

import java.util.ArrayList;

public class SiparisMusteriActivity extends AppCompatActivity {
    private Button siparisYap;
    private TextView totalPrice;
    private double toplam = 0;
    private LinearLayout yemekler;
    private ArrayList<Yemek> siparisVerilenYemekler;
    private Musteri musteri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_musteri);
        init();
        MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(SiparisMusteriActivity.this);
        YemekVeriIletisimi yvi = new YemekVeriIletisimi(SiparisMusteriActivity.this);
        Intent intent = getIntent();
        musteri = mvi.idDenMusteriOlustur(intent.getIntExtra("MusteriID",0));
        ArrayList<Yemek> foods = yvi.yemekListesi();
        for (final Yemek yemek : foods){
            final CheckBox tmp = new CheckBox(SiparisMusteriActivity.this);
            tmp.setTextColor(Color.DKGRAY);
            tmp.setTextSize(26);
            tmp.setText(yemek.getIsim() + " " + yemek.getFiyat() + "₺");
            tmp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    double fiyat = yemek.getFiyat();
                    if(tmp.isChecked()){
                        toplam += fiyat;
                        totalPrice.setText("Toplam : " + String.valueOf(toplam) + "₺");
                        siparisVerilenYemekler.add(yemek);
                    }
                    else{
                        toplam -= fiyat;
                        totalPrice.setText("Toplam : " + String.valueOf(toplam) + "₺");
                        siparisVerilenYemekler.remove(yemek);
                    }
                }
            });
            yemekler.addView(tmp);
        }
        siparisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toplam == 0){
                    Toast.makeText(getApplicationContext(),"Bir ürün seçmelisiniz",Toast.LENGTH_SHORT).show();
                    return;
                }
                //siparis yapma islemi gerceklestirilecek
                SiparisVeriIletsimi svi = new SiparisVeriIletsimi(SiparisMusteriActivity.this);
                Siparis s = new Siparis(musteri,siparisVerilenYemekler);
                svi.siparisEkle(s);
                Toast.makeText(getApplicationContext(), "Siparisiniz Olusturulmustur", Toast.LENGTH_SHORT).show();

                SiparisMusteriActivity.this.finish();
            }
        });
    }

    private void init(){
        siparisYap = (Button) findViewById(R.id.siparis_yap);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        yemekler = (LinearLayout) findViewById(R.id.yemeklerLayout);
        siparisVerilenYemekler = new ArrayList<>();

    }

    @Override
    public void onBackPressed() {
        for(int i=0 ; i<yemekler.getChildCount() ; i++){
            CheckBox tmp = (CheckBox) yemekler.getChildAt(i);
            tmp.setChecked(false);
        }
        super.onBackPressed();
    }
}
