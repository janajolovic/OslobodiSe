package com.example.oslobodiseresi.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Models.RegistarModel;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

public class RegistracijaActivity extends ToolbarNavigacijaSetup {

    private EditText ime;
    private EditText email;
    private EditText lozinka;
    private EditText lozinkaPonovi;
    private EditText brojTelefona;
    private Button dugme;
    private TextView pogresneLozinke;
    private TextView registerToLogin;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_registracija);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);

        ime = findViewById(R.id.registracijaIme);
        email = findViewById(R.id.registracijaEmail);
        lozinka = findViewById(R.id.registracijaLozinka);
        lozinkaPonovi = findViewById(R.id.registracijaLozinkaPonovi);
        brojTelefona = findViewById(R.id.registracijaTelefon);
        dugme = findViewById(R.id.registracijaBtn);
        pogresneLozinke = findViewById(R.id.registracijaPogresneLozinke);
        registerToLogin = findViewById(R.id.txtRegisterToLogin);

        // kada se potvrdi registracija i ako su uneti podaci ispravni, korisnik se dodaje u bazu
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

                k.observe(RegistracijaActivity.this, new Observer<Korisnik>() {
                    @Override
                    public void onChanged(Korisnik korisnik) {
                        if(korisnik==null){
                            Toast.makeText(RegistracijaActivity.this, "Neispravna registracija", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Utils.getInstance().SacuvajKorisnika(korisnik);
                        Intent intent = new Intent(RegistracijaActivity.this, OdabirSlikeNakonRegistracije.class);
                        startActivity(intent);
                    }
                });

            }
        });
        // ukoliko korisnik zeli da se vrati na stranicu za prijavu
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistracijaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}