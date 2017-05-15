package com.example.tugberk.restoran.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Siparis;
import com.example.tugberk.restoran.Model.Yemek;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.SiparisVeriIletsimi;
import com.example.tugberk.restoran.VeriIletisimi.SiparisYemekVeriIletisimi;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RestoranSiparis extends AppCompatActivity {
    private ListView siparisListesi;
    private Button siparisGonder;
    private ArrayList<String> siparisBilgiler;
    private int pozisyon  = -1;
    private ArrayList<Siparis> siparisler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_siparis);
        init();
        siparisleriYukle();
        handleListeners();


    }

    private void siparisleriYukle(){
        siparisBilgiler = new ArrayList<>();
        //siparis bilgileri veri tabanindan alinacak
        SiparisVeriIletsimi svi = new SiparisVeriIletsimi(RestoranSiparis.this);
        siparisler = svi.siparisListesiniDondur();
        for (Siparis s : siparisler){
            double toplamFiyat = 0;
            String yemekler = "";
            for(Yemek y : s.getYemekler()){
                toplamFiyat += y.getFiyat();
                yemekler += y.getIsim() + ", ";
            }
            String bilgi = "Sipariş Sahibi : " + s.getMusteri().getAd() + " " + s.getMusteri().getSoyAd() +
                    "\nAdres : " + s.getMusteri().getAdres() + "\nTelefon : " + s.getMusteri().getTelefon() +
                    "\nSiparis Verilen Yemekler : " + yemekler + "\nToplam Fiyat : " + toplamFiyat + "₺";
            siparisBilgiler.add(bilgi);
        }
        listViewUpdate();
    }
    private void handleListeners(){
        siparisListesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pozisyon = i;
                Toast.makeText(getApplicationContext(), siparisBilgiler.get(i), Toast.LENGTH_SHORT).show();
            }
        });
        siparisGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pozisyon != -1){
                    SiparisVeriIletsimi svi = new SiparisVeriIletsimi(RestoranSiparis.this);
                    Siparis siparis = siparisler.get(pozisyon);
                    svi.siparisSil(siparis);
                    Toast.makeText(getApplicationContext(),"Silindi",Toast.LENGTH_SHORT).show();
                    //bilgiye sahip siparis veri tabanindan silinecek
                    siparisleriYukle();
                    listViewUpdate();
                }
                pozisyon = -1;
            }
        });

    }

    private void init(){
        siparisListesi = (ListView) findViewById(R.id.siparisListesi);
        siparisGonder = (Button) findViewById(R.id.siparisGonderBtn);
    }

    private void listViewUpdate(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.list_design, siparisBilgiler);
        siparisListesi.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        RestoranSiparis.this.finish();
        super.onBackPressed();
    }
}
