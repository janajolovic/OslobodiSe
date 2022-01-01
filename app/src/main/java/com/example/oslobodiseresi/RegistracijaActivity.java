package com.example.oslobodiseresi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistracijaActivity extends AppCompatActivity {

    private EditText ime;
    private EditText email;
    private EditText lozinka;
    private EditText lozinkaPonovi;
    private EditText brojTelefona;
    private Button dugme;
    private TextView pogresneLozinke;
    private TextView registerToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        ime = findViewById(R.id.registracijaIme);
        email = findViewById(R.id.registracijaEmail);
        lozinka = findViewById(R.id.registracijaLozinka);
        lozinkaPonovi = findViewById(R.id.registracijaLozinkaPonovi);
        brojTelefona = findViewById(R.id.registracijaTelefon);
        dugme = findViewById(R.id.registracijaBtn);
        pogresneLozinke = findViewById(R.id.registracijaPogresneLozinke);
        registerToLogin = findViewById(R.id.txtRegisterToLogin);


//        TODO: da nema enter kad se unosi ime jer posle 'nestane'
        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!lozinka.getText().toString().equals(lozinkaPonovi.getText().toString())) {
                    pogresneLozinke.setVisibility(View.VISIBLE);
                    return;
                }
                pogresneLozinke.setVisibility(View.INVISIBLE);


                MutableLiveData<Korisnik> k = UserRepository.getInstance(MainApplication.apiManager).Register(new RegistarModel(
                   ime.getText().toString(),
                   email.getText().toString(),
                   lozinka.getText().toString(),
                   brojTelefona.getText().toString()
                ));

                k.observe(RegistracijaActivity.this, funk->{
                    if(k.getValue()!=null){
                        Toast.makeText(RegistracijaActivity.this, "Korisnik je "+k.getValue().getIme(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistracijaActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(RegistracijaActivity.this, "Korisnik je null :((((", Toast.LENGTH_LONG).show();

                });
            }
        });
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistracijaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}