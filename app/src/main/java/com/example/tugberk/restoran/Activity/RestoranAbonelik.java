package com.example.tugberk.restoran.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tugberk.restoran.Model.Abonelik;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.AbonelikVeriIletisimi;

import java.util.ArrayList;

public class RestoranAbonelik extends AppCompatActivity {
    private ListView aboneliklerListesi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_abonelik);
        init();
        abonelikleriYukle();

    }
    private void init(){
        aboneliklerListesi = (ListView) findViewById(R.id.aboneliklerListesi);
    }
    private void abonelikleriYukle(){

        AbonelikVeriIletisimi avi = new AbonelikVeriIletisimi(RestoranAbonelik.this);
        ArrayList<Abonelik> abonelikler = avi.aktifAbonelikleriDondur();
        ArrayList<String> abonelikBilgiler = new ArrayList<>();
        for(Abonelik a : abonelikler){
            abonelikBilgiler.add(a.getMusteri().getAd() + " " +a.getMusteri().getSoyAd() +
                    " " + a.getMusteri().getTelefon() +  "\n" +
                    a.getBaslangicTarihi().tarihToString() + " - " +
                    a.getBitisTarihi().tarihToString() + " " +
                    a.getRezervasyonSaati().saatToString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.list_design, abonelikBilgiler);
        aboneliklerListesi.setAdapter(adapter);

    }

}
