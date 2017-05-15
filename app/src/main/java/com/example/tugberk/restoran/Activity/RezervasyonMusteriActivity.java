package com.example.tugberk.restoran.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.Model.Rezervasyon;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.Tarih;
import com.example.tugberk.restoran.VeriIletisimi.MusteriVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.RezervasyonVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.VeriIletisimi;

public class RezervasyonMusteriActivity extends AppCompatActivity {
    private Button rezervasyonSil;
    private Button rezervasyonYap;
    private ListView rezList;
    private String rezInfo = "";
    private String silInfo = "";
    private String kisiSayisi;
    private int silPoz = -1;
    private ArrayList<String> musteriRezervasyonlari;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private java.text.SimpleDateFormat sdf;
    private Calendar cal;
    private EditText kisiler;
    private int sene, ay, gun, saat, dk;
    private boolean cancelClicked = false;
    private Musteri musteri;
    private RezervasyonVeriIletisimi rvi;
    private MusteriVeriIletisimi mvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervasyon_musteri);
        init();
        Intent intent = getIntent();

        musteri = mvi.idDenMusteriOlustur(intent.getIntExtra("MusteriID",0));
        rezervasyonlariYukle();
        handleListeners();

    }

    void init(){
        rezervasyonSil = (Button) findViewById(R.id.rezSil);
        rezervasyonYap = (Button) findViewById(R.id.yeniRezYap);
        rezList = (ListView) findViewById(R.id.rezervasyonList);
        cal = Calendar.getInstance();
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH.mm");
        musteriRezervasyonlari = new ArrayList<>();
        rvi = new RezervasyonVeriIletisimi(RezervasyonMusteriActivity.this);
        mvi = new MusteriVeriIletisimi(RezervasyonMusteriActivity.this);

    }
    void rezervasyonlariYukle(){    //rezervasyonlarin veritabanindan alinip listview icine yuklenme islemi
        musteri.setRezervasyonlar(mvi.rezervasyonlarıGoster(musteri));
        ArrayList<Rezervasyon> r = musteri.getRezervasyonlar();
        musteriRezervasyonlari.clear();
        for(Rezervasyon rezervasyon : r){
            musteriRezervasyonlari.add(rezervasyon.rezervasyonToString());
        }
        listViewUpdate();
    }

    void handleListeners(){
        rezList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                silInfo = rezList.getItemAtPosition(i).toString();
                Log.e("silinfo", silInfo);
                silPoz = i;
                Toast.makeText(getApplicationContext(), silInfo + "\nSecildi", Toast.LENGTH_SHORT).show();
            }
        });

        rezervasyonSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listview'daki string bilgisine sahip olan rezervasyon veritabanindan silinecek
                //silInfo bilgisine sahip rezervasyonun silinmesi islemi burada yapilacak
                //database icerisinden delete rezervasyon methodu calistirilacak

                if(silPoz != -1){
                    String tarih[] = silInfo.split(" ");
                    Rezervasyon r = new Rezervasyon(Integer.parseInt(tarih[5]),
                            Tarih.toTarih(tarih[0],tarih[1]),musteri);
                    Log.e("rezbilgi ? ", r.rezervasyonToString());
                    rvi.rezervasyonSilme(r);
                    rezervasyonlariYukle();
                    Toast.makeText(getApplicationContext(), silInfo + "\nSilindi", Toast.LENGTH_SHORT).show();
                }
                silPoz =-1;
            }
        });

        rezervasyonYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(RezervasyonMusteriActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        saat = hour;
                        dk = 0;
                    }
                }, 8, 0, true);
                timePickerDialog.setTitle("Saati Seçiniz");
                timePickerDialog.setCancelable(true);
                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        cancelClicked = true;
                    }
                });
                timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(!cancelClicked) {
                            cal.set(sene, ay, gun, saat, dk);
                            rezInfo = sdf.format(cal.getTime());
                            String tarih[] = rezInfo.split(" ");
                            kisiSayisi = kisiler.getText().toString();
                            Rezervasyon r = new Rezervasyon(Integer.parseInt(kisiSayisi),
                                    Tarih.toTarih(tarih[0],tarih[1]),musteri);

                            boolean b = rvi.rezervasyonYap(r);
                            if(b){
                                rezervasyonlariYukle();
                            }else{
                                Toast.makeText(getApplicationContext(),"Rezervasyon yapılırken bir hata oluştu",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                        cancelClicked = false;
                        rezInfo = "";
                    }
                });
                timePickerDialog.create();
                datePickerDialog = new DatePickerDialog(RezervasyonMusteriActivity.this,null, 2017, 4, 25);
                datePickerDialog.setTitle("Tarihi Seçiniz");
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        datePickerDialog.hide();
                    }
                });
                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        datePickerDialog.hide();
                        timePickerDialog.show();
                        DatePicker datePicker= datePickerDialog.getDatePicker();
                        sene = datePicker.getYear();
                        ay = datePicker.getMonth();
                        gun = datePicker.getDayOfMonth();
                    }
                });
                AlertDialog.Builder kisiD = new AlertDialog.Builder(RezervasyonMusteriActivity.this, R.style.Theme_Design_BottomSheetDialog);
                kisiler = new EditText(RezervasyonMusteriActivity.this);
                kisiler.setInputType(InputType.TYPE_CLASS_NUMBER);
                kisiler.setHint("Kişi Sayısı");
                kisiD.setTitle("Rezervasyon için kişi sayısını lütfen giriniz");
                kisiD.setIcon(R.drawable.table_icon);
                kisiD.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        kisiSayisi = kisiler.getText().toString();
                        datePickerDialog.create();
                        datePickerDialog.show();
                    }
                });
                kisiD.setView(kisiler);
                kisiD.create().show();

            }
        });
    }
    private void listViewUpdate(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.list_design,  musteriRezervasyonlari);
        rezList.setAdapter(adapter);
    }

}
