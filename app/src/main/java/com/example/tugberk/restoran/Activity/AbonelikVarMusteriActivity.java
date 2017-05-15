package com.example.tugberk.restoran.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Abonelik;
import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Model.Rezervasyon;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.AbonelikVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.MusteriVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.RezervasyonVeriIletisimi;

public class AbonelikVarMusteriActivity extends AppCompatActivity {
    private Button abonelikIptal;
    private TextView abonelikBilgileri;
    private Musteri musteri;
    private Abonelik abonelik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonelik_var_musteri);
        init();
        Intent i =  getIntent();
        MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(AbonelikVarMusteriActivity.this);
        AbonelikVeriIletisimi avi = new AbonelikVeriIletisimi(AbonelikVarMusteriActivity.this);
        musteri = mvi.idDenMusteriOlustur(i.getIntExtra("MusteriID",0));
        abonelik = avi.musteriIdSindenAbonelikDondur(musteri);

        abonelikBilgileri.setText("ABONELİK BİLGİLERİ\nBaslangıç Tarihi : " +
                abonelik.getBaslangicTarihi().tarihToString() + "\nBitiş Tarihi : " +
                abonelik.getBitisTarihi().tarihToString() + "\nRezervasyon Saati : " +
                abonelik.getRezervasyonSaati().saatToString() + "\nRandevu Sıklığı : " +
                abonelik.getFrekans());

        abonelikIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbonelikVeriIletisimi avi = new AbonelikVeriIletisimi(AbonelikVarMusteriActivity.this);
                MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(AbonelikVarMusteriActivity.this);
                RezervasyonVeriIletisimi rvi = new RezervasyonVeriIletisimi(AbonelikVarMusteriActivity.this);
                avi.abonelikIptali(abonelik);
                for(Rezervasyon r : mvi.rezervasyonlarıGoster(musteri)){
                    rvi.rezervasyonSilme(r);
                }
                Toast.makeText(getApplicationContext(),"Abonelik iptal edildi",Toast.LENGTH_SHORT).show();
                AbonelikVarMusteriActivity.this.finish();
            }
        });

    }
    private void init(){
        abonelikIptal = (Button) findViewById(R.id.abonelikIptalEt);
        abonelikBilgileri = (TextView) findViewById(R.id.musteriAbonelikBilgileri);
    }

    @Override
    public void onBackPressed() {
        AbonelikVarMusteriActivity.this.finish();
        super.onBackPressed();
    }
}
