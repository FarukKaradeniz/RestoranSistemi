package com.example.tugberk.restoran.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tugberk.restoran.Model.Musteri;
import com.example.tugberk.restoran.R;
import com.example.tugberk.restoran.VeriIletisimi.MusteriVeriIletisimi;

public class LoginActivity extends AppCompatActivity {
    private EditText mailGirisEdt;
    private EditText sifreGirisEdt;
    private Button girisYap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        handleListeners();
    }
    private void init(){
        mailGirisEdt = (EditText) findViewById(R.id.mailGirisEdt);
        sifreGirisEdt = (EditText) findViewById(R.id.sifreGirisEdt);
        girisYap = (Button) findViewById(R.id.girisYap);
    }
    private void handleListeners(){
        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mailGirisEdt.getText().toString();
                String sifre = sifreGirisEdt.getText().toString();
                sifreGirisEdt.setText("");
                if(!isInfoEmpty(new String[]{mail, sifre})){
                    if(mail.equals(Musteri.RestoranLogIn.EMAIL) &&
                            sifre.equals(Musteri.RestoranLogIn.SIFRE)){
                        startActivity(new Intent(getApplicationContext(),RestoranMenu.class));
                        LoginActivity.this.finish();
                        return;
                    }
                    MusteriVeriIletisimi mvi = new MusteriVeriIletisimi(LoginActivity.this);
                    Musteri m = mvi.logIn(mail, sifre);
                    if(m == null){
                        Toast.makeText(LoginActivity.this, "E-Posta veya şifre hatalı", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), MusteriMenu.class);
                        intent.putExtra("MusteriID",m.getId());
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }

                }
                else{
                    if(mail.isEmpty())
                        mailGirisEdt.setError("Email boş bırakılamaz");
                    else
                        sifreGirisEdt.setError("Şifrenizi giriniz");
                }

            }
        });
    }
    private boolean isInfoEmpty(String [] info){
        for (String s : info){
            if(s.isEmpty())
                return true;
        }
        return false;
    }
}
