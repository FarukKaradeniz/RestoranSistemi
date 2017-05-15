package com.example.tugberk.restoran.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Yemek;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.YemekVeriIletisimi;

import java.util.ArrayList;

public class RestoranYemek extends AppCompatActivity {
    ListView yemekler;
    EditText yemekIsim;
    EditText yemekFiyat;
    Button yemekSil;
    Button yemekGuncelle;
    Button yemekEkle;
    ArrayList<Yemek> foods;
    int pozisyon = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_yemek);
        init();
        yemekleriListele();
        handleListeners();



    }
    private void init(){
        yemekler = (ListView) findViewById(R.id.yemekListesi);
        yemekEkle = (Button) findViewById(R.id.yemekEkleBtn);
        yemekFiyat = (EditText) findViewById(R.id.yemek_fiyat);
        yemekSil = (Button) findViewById(R.id.yemekSilBtn);
        yemekGuncelle = (Button) findViewById(R.id.yemekGuncelleBtn);
        yemekIsim = (EditText) findViewById(R.id.yemek_isim);
    }
    private void yemekleriListele(){
        YemekVeriIletisimi yvi = new YemekVeriIletisimi(RestoranYemek.this);
        foods = yvi.yemekListesi();
        listViewUpdate();
    }
    private void handleListeners(){
        yemekler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String yemekInfo = foods.get(i).getIsim();
                double fiyatInfo = foods.get(i).getFiyat();
                pozisyon = i;
                yemekIsim.setText(yemekInfo);
                yemekFiyat.setText(String.valueOf(fiyatInfo));
                //yemeklerin bilgileri edittext icerisine yuklenecek
            }
        });
        yemekSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yemekInfo = yemekIsim.getText().toString();
                YemekVeriIletisimi yvi = new YemekVeriIletisimi(RestoranYemek.this);
                Yemek y = yvi.isimdenYemekDondur(yemekInfo);
                if(y != null){
                    yvi.yemekSilme(y);
                    yemekIsim.setText("");
                    yemekFiyat.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"Yemek zaten mevcut değil",Toast.LENGTH_SHORT).show();
                }
                yemekleriListele();
            }
        });
        yemekEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YemekVeriIletisimi yvi = new YemekVeriIletisimi(RestoranYemek.this);
                String yemekInfo = yemekIsim.getText().toString();
                String fiyatInfo = yemekFiyat.getText().toString();
                Yemek y = new Yemek(yemekInfo,Double.parseDouble(fiyatInfo));
                if(!yvi.yemekKaydet(y)){
                    Toast.makeText(getApplicationContext(),"Yemek zaten mevcut",Toast.LENGTH_SHORT).show();
                }
                yemekleriListele();
            }
        });
        yemekGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yemekInfo = yemekIsim.getText().toString();
                String fiyatInfo = yemekFiyat.getText().toString();
                YemekVeriIletisimi yvi = new YemekVeriIletisimi(RestoranYemek.this);
                Yemek y = yvi.isimdenYemekDondur(yemekInfo);
                if(y != null){
                    yvi.fiyatGuncelle(y,Double.parseDouble(fiyatInfo));
                }else{
                    Toast.makeText(getApplicationContext(),"Yemek mevcut değil",Toast.LENGTH_SHORT).show();
                    return;
                }
                yemekleriListele();
            }
        });
    }

    private void listViewUpdate(){
        ArrayList<String> yemekMetin = new ArrayList<>();
        for(Yemek y : foods){
            yemekMetin.add(y.getIsim() + " " + String.valueOf(y.getFiyat()) + "₺");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.list_design, yemekMetin);
        yemekler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        RestoranYemek.this.finish();
        super.onBackPressed();
    }
}
