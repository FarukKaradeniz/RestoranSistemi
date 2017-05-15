package com.example.tugberk.restoran.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Abonelik;
import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.Tarih;
import com.example.tugberk.restoran.VeriIletisimi.AbonelikVeriIletisimi;
import com.example.tugberk.restoran.VeriIletisimi.MusteriVeriIletisimi;

import java.util.ArrayList;
import java.util.Calendar;

public class AbonelikYokMusteriActivity extends AppCompatActivity {
    private Button aboneOl;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private java.text.SimpleDateFormat sdf;
    private EditText frekansEdt;
    private Calendar cal;
    private String abonelikInfo = "";
    private int sene, ay, gun, saat, dk, frekans, sure, secilen;
    ArrayList<String> arrayList;
    private boolean cancelClicked = false;
    private Musteri musteri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonelik_yok_musteri);
        init();
        Intent i = getIntent();
        MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(AbonelikYokMusteriActivity.this);
        musteri = mvi.idDenMusteriOlustur(i.getIntExtra("MusteriID",0));

        aboneOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(AbonelikYokMusteriActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                            abonelikInfo = sdf.format(cal.getTime());
                            //spinner diyalog olusturulacak
                            AlertDialog.Builder sureDialog = new AlertDialog.Builder(AbonelikYokMusteriActivity.this,
                                    R.style.Theme_Design_BottomSheetDialog);
                            Spinner s = sureListesi();
                            sureDialog.setIcon(R.drawable.table_icon);
                            sureDialog.setTitle("Aboneliğin süresini seçiniz");
                            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    secilen = i;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            sureDialog.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    frekans = Integer.parseInt(frekansEdt.getText().toString());
                                    aboneligiOlustur();
                                }
                            });

                            sureDialog.setView(s);
                            sureDialog.create().show();

                        }
                        cancelClicked = false;
                    }
                });
                timePickerDialog.create();
                datePickerDialog = new DatePickerDialog(AbonelikYokMusteriActivity.this,null, 2017, 4, 25);
                datePickerDialog.setTitle("Abonelik Başlangıç Tarihi");
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
                AlertDialog.Builder frekansDialog = new AlertDialog.Builder(AbonelikYokMusteriActivity.this, R.style.Theme_Design_BottomSheetDialog);
                frekansEdt = new EditText(AbonelikYokMusteriActivity.this);
                frekansEdt.setInputType(InputType.TYPE_CLASS_NUMBER);
                frekansEdt.setHint("Abonelik günü sıklığı");
                frekansDialog.setTitle("Kaç gün aralık istediğinizi giriniz");
                frekansDialog.setIcon(R.drawable.table_icon);
                frekansDialog.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        datePickerDialog.create();
                        datePickerDialog.show();
                    }
                });
                frekansDialog.setView(frekansEdt);
                frekansDialog.create().show();

            }
        });
    }
    private void init(){
        aboneOl = (Button) findViewById(R.id.abonelikOlustur);
        cal = Calendar.getInstance();
        sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH.mm");
    }

    private Spinner sureListesi(){
        Spinner s = new Spinner(AbonelikYokMusteriActivity.this);
        arrayList = new ArrayList<>();
        arrayList.add("1 Hafta");
        arrayList.add("1 Ay");
        arrayList.add("3 Ay");
        arrayList.add("6 Ay");
        ArrayAdapter<String> adapter = new ArrayAdapter(AbonelikYokMusteriActivity.this,
                android.R.layout.simple_spinner_dropdown_item, arrayList);
        s.setAdapter(adapter);
        return s;
    }


    @Override
    public void onBackPressed() {
        AbonelikYokMusteriActivity.this.finish();
        super.onBackPressed();
    }
    public void aboneligiOlustur(){
        String[] temp = abonelikInfo.split(" ");
        Tarih baslangic = Tarih.toTarih(temp[0],temp[1]);
        AbonelikVeriIletisimi avi = new AbonelikVeriIletisimi(AbonelikYokMusteriActivity.this);
        Tarih bitis = Tarih.toTarih(temp[0],temp[1]);
        if(secilen == 0){
            bitis.add(Calendar.DATE,7);
        }else if(secilen == 1){
            bitis.add(Calendar.MONTH,1);
        }else if(secilen == 2){
            bitis.add(Calendar.MONTH,3);
        }else{
            bitis.add(Calendar.MONTH,6);
        }
        Abonelik abonelik = new Abonelik(baslangic,bitis,frekans,baslangic,musteri);
        avi.abonelikOlustur(abonelik);
        Toast.makeText(getApplicationContext(),"Abonelik Başarıyla Oluşturuldu",Toast.LENGTH_SHORT).show();
        //avi.kapat();
        AbonelikYokMusteriActivity.this.finish();
    }
}
