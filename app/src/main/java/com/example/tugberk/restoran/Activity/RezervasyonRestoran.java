package com.example.tugberk.restoran.Activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Rezervasyon;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.RezervasyonVeriIletisimi;

import java.util.ArrayList;
import java.util.Calendar;

public class RezervasyonRestoran extends AppCompatActivity {
    private Button rezGoruntule;
    private ListView rezList;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private java.text.SimpleDateFormat sdf;
    private Calendar cal;
    private String rezInfo = "";
    private ArrayList<String> rezervasyonlarListesi;
    private int sene, ay, gun, saat, dk;
    private boolean cancelClicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervasyon_restoran);
        init();
        handleListeners();


    }
    private void init(){
        rezGoruntule = (Button) findViewById(R.id.rezGoruntuleRestoran);
        rezList = (ListView) findViewById(R.id.rezervasyonListRestoran);
        cal = Calendar.getInstance();
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH.mm");
        rezervasyonlarListesi = new ArrayList<>();
    }
    private void rezervasyonlariYukle(String info){
        rezervasyonlarListesi.clear();
        String[] bilgiler = info.split(" ");
        RezervasyonVeriIletisimi rvi = new RezervasyonVeriIletisimi(RezervasyonRestoran.this);
        ArrayList<Rezervasyon> r = rvi.zamanaGoreRezervasyonlar(bilgiler[0], bilgiler[1]);
        for(Rezervasyon rezervasyon : r){
            rezervasyonlarListesi.add(rezervasyon.getMusteri().getAd() + " " +
                    rezervasyon.getMusteri().getSoyAd() + "\nKişi Sayısı : " + rezervasyon.getKisiSayisi());
        }
        listViewUpdate();
    }
    private void handleListeners(){
        rezGoruntule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(RezervasyonRestoran.this, new TimePickerDialog.OnTimeSetListener() {
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
                            Toast.makeText(getApplicationContext(), rezInfo, Toast.LENGTH_SHORT).show();
                            rezervasyonlariYukle(rezInfo);
                        }
                        cancelClicked = false;
                        rezInfo = "";
                    }
                });
                timePickerDialog.create();
                datePickerDialog = new DatePickerDialog(RezervasyonRestoran.this,null, 2017, 4, 25);
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
                datePickerDialog.create();
                datePickerDialog.show();

            }
        });
    }
    private void listViewUpdate(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.list_design, rezervasyonlarListesi);
        rezList.setAdapter(adapter);
    }
}
