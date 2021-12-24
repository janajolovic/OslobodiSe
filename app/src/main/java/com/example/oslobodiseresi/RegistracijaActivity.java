package com.example.oslobodiseresi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistracijaActivity extends AppCompatActivity {

    private EditText ime;
    private EditText prezime;
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
        prezime = findViewById(R.id.registracijaPrezime);
        email = findViewById(R.id.registracijaEmail);
        lozinka = findViewById(R.id.registracijaLozinka);
        lozinkaPonovi = findViewById(R.id.registracijaLozinkaPonovi);
        brojTelefona = findViewById(R.id.registracijaTelefon);
        dugme = findViewById(R.id.registracijaBtn);
        pogresneLozinke = findViewById(R.id.registracijaPogresneLozinke);
        registerToLogin = findViewById(R.id.txtRegisterToLogin);

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!lozinka.getText().toString().equals(lozinkaPonovi.getText().toString())) {
                    pogresneLozinke.setVisibility(View.VISIBLE);
                    return;
                }
                pogresneLozinke.setVisibility(View.INVISIBLE);
                Utils.getInstance().addToKorisnici(new Korisnik(
                    ime.getText().toString(),
                    prezime.getText().toString(),
                    email.getText().toString(),
                    lozinka.getText().toString(),
                    brojTelefona.getText().toString()
                ));

                Intent intent = new Intent(RegistracijaActivity.this, MainActivity.class);
                startActivity(intent);
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